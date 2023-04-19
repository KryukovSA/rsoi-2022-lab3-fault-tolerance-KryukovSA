package com.example.ratingservice.controller;

import com.example.ratingservice.service.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/rating")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping
    public Integer getUserRating(@RequestParam("username") String username) {
        return ratingService.getUserRating(username);
    }

    @PostMapping("/decrease")
    public ResponseEntity<?> decreaseUserRating(@RequestParam("username") String username,
                                                @RequestParam("expired") Boolean expired,
                                                @RequestParam("badCondition") Boolean badCondition) {
        ratingService.decreaseRating(username, expired, badCondition);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/increase")
    public ResponseEntity<?> increaseUserRating(@RequestParam("username") String username) {
        ratingService.increaseRating(username);
        return ResponseEntity.noContent().build();
    }

}
