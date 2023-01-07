package com.example.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Controller
@RequestMapping("/api/v1/rating")
public class RatingGatewayController {


    public static final String ratingUrl = "http://rating:8050/api/v1/rating";

    @GetMapping
    public ResponseEntity<HashMap> getUserRating(@RequestHeader("X-User-Name") String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url = ratingUrl + "?username=" + username;
        Integer result = restTemplate.getForObject(url, Integer.class);
        HashMap<String, Integer> raiting = new HashMap<>();
        raiting.put("stars", result);
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
