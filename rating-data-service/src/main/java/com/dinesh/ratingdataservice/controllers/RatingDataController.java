package com.dinesh.ratingdataservice.controllers;

import com.dinesh.ratingdataservice.models.Rating;
import com.dinesh.ratingdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingDataController {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating(movieId,4);
    }

    @RequestMapping("users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId){
        List<Rating> ratingList = Arrays.asList(
                new Rating("100",3),
                new Rating("200",4)
        );
        UserRating userRating = new UserRating();
        userRating.setRatingList(ratingList);
        return userRating;
    }
}
