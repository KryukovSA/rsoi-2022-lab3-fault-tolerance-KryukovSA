package com.example.gateway.controller;

import com.example.libraryservice.model.Books;
import com.example.request1.requests.UnavalableAnswer;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Library;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/libraries")
public class LibraryGatewayController {
    private static Integer maxCountErr = 8;
    private final TaskScheduler scheduler;
    private Integer countErr = 0;
    private final Runnable healthCheck =
            new Runnable() {
                @Override
                public void run() {
                    try {
                        RestTemplate restTemplate = new RestTemplate();
                        restTemplate.getForEntity("http://library:8060/manage/health", ResponseEntity.class);
                        countErr = 0;
                    } catch (Exception e) {
                        scheduler.schedule(this, new Date(System.currentTimeMillis() + 10000L));
                    }
                }
            };
    public static final String libraryUrl = "http://library:8060/api/v1/libraries";

    @GetMapping()
    public ResponseEntity<?> getLibsInCity(@RequestParam("city") String city) {
        String url = libraryUrl + "?city=" + city;
        RestTemplate restTemplate = new RestTemplate();
        List<Library> result = null;
                restTemplate.getForObject(url, List.class);
        HashMap<String, Object> output = new HashMap<>();
        try {
            if(countErr >= maxCountErr){
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new UnavalableAnswer("Library Service unavailable"));
            } else {
                result = restTemplate.getForObject(url, List.class);
                output.put("page", 1);
                output.put("pageSize", 1);
                output.put("totalElements", result.size());
                output.put("items", result);
                if(result != null)
                    countErr = 0;
            }
        } catch (Exception exception){
            countErr = countErr + 1;
            ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new UnavalableAnswer("Library Service unavailable"));
        }
        return ResponseEntity.ok(output);
    }

    @GetMapping(value = "/{libraryUid}/books")
    public ResponseEntity<?> getLibBooks(@PathVariable("libraryUid") UUID libraryUid,
                                         @RequestParam("showAll") Boolean showAll) {
        String url = libraryUrl +'/' +libraryUid + "/books?showAll=" + showAll;
        RestTemplate restTemplate = new RestTemplate();
        List<Books> result = null;
        HashMap<String, Object> output = new HashMap<>();
        try {
            if(countErr >= maxCountErr){
                scheduler.schedule(healthCheck, new Date(System.currentTimeMillis() + 10000L));
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new UnavalableAnswer("Library Service unavailable"));
            } else {
                result = restTemplate.getForObject(url, List.class);
                output.put("page", 1);
                output.put("pageSize", 1);
                output.put("totalElements", result.size());
                output.put("items", result);
                if(result != null)
                    countErr = 0;
            }
        } catch (Exception exception){
            countErr = countErr + 1;
            ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new UnavalableAnswer("Library Service unavailable"));
        }


        return ResponseEntity.ok(output);
    }

}
