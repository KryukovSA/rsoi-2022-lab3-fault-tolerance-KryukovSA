package com.example.gateway.controller;

import com.example.libraryservice.model.Books;
import org.apache.tomcat.jni.Library;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/libraries")
public class LibraryGatewayController {

    public static final String libraryUrl = "http://library:8060/api/v1/libraries";

    @GetMapping()
    public ResponseEntity<HashMap> getLibsInCity(@RequestParam("city") String city) {
        String url = libraryUrl + "?city=" + city;
        RestTemplate restTemplate = new RestTemplate();
        List<Library> result = restTemplate.getForObject(url, List.class);
        HashMap<String, Object> output = new HashMap<>();
        output.put("page", 1);
        output.put("pageSize", 1);
        output.put("totalElements", result.size());
        output.put("items", result);
        return ResponseEntity.ok(output);
    }

    @GetMapping(value = "/{libraryUid}/books")
    public ResponseEntity<HashMap> getLibBooks(@PathVariable("libraryUid") UUID libraryUid,
                                         @RequestParam("showAll") Boolean showAll) {
        String url = libraryUrl +'/' +libraryUid + "/books?showAll=" + showAll;
        RestTemplate restTemplate = new RestTemplate();
        List<Books> result = restTemplate.getForObject(url, List.class);
        HashMap<String, Object> output = new HashMap<>();
        output.put("page", 1);
        output.put("pageSize", 1);
        output.put("totalElements", result.size());
        output.put("items", result);
        return ResponseEntity.ok(output);
    }

}
