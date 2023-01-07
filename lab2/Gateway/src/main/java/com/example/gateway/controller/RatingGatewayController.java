package com.example.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Controller
@Slf4j
@RequestMapping("/api/v1/rating")
public class RatingGatewayController {


    public static final String ratingUrl = "http://rating:8050/api/v1/rating";

    @GetMapping
    public ResponseEntity<?> getUserRating(@RequestHeader("X-User-Name") String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url = ratingUrl + "?username=" + username;
        HashMap<String, Integer> raiting = new HashMap<>();
        Integer result = null;
        try {
            result = restTemplate.getForObject(url, Integer.class);
            raiting.put("stars", result);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Rating Service unavailable");
        }
        return ResponseEntity.ok(raiting);
    }

    @PostMapping("/decrease")
    public ResponseEntity<?> decreaseUserRating(@RequestParam("username") String username,
                                                @RequestParam("expired") Boolean expired,
                                                @RequestParam("badCondition") Boolean badCondition) {
        RestTemplate restTemplate = new RestTemplate();
        String url = ratingUrl + "/decrease" + "?username=" + username + "&expired=" + expired + "&badCondition=" + badCondition;
        restTemplate.postForLocation(url, null);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/increase")
    public ResponseEntity<?> increaseUserRating(@RequestParam("username") String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url = ratingUrl + "/increase" + "?username=" + username;
        restTemplate.postForLocation(url, null);
        return ResponseEntity.noContent().build();
    }

}
