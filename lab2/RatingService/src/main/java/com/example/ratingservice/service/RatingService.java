package com.example.ratingservice.service;

import com.example.ratingservice.model.Rating;
import com.example.ratingservice.repository.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RatingService {
    private final RatingRepository ratingRepository;

    public Integer getUserRating(String username) {
        Rating rating = ratingRepository.findByUsername(username);
        if(rating == null) {
            rating = new Rating();
            rating.setStars(75);
            rating.setUsername(username);
            ratingRepository.save(rating);
        }
        return rating.getStars();
    }

    public void decreaseRating(String username, Boolean expired, Boolean badCondition) {
        Rating rating = ratingRepository.findByUsername(username);
        Integer stars = rating.getStars();
        if(expired == true)
            stars -= 10;
        if(badCondition == true)
            stars -= 10;
        if(stars <= 0)
            stars = 1;
        rating.setStars(stars);
        ratingRepository.save(rating);
    }

    public void increaseRating(String username) {
        Rating rating = ratingRepository.findByUsername(username);
        Integer stars = rating.getStars() + 1;
        if (stars > 100)
            stars = 100;
        rating.setStars(stars);
        ratingRepository.save(rating);
    }
}
