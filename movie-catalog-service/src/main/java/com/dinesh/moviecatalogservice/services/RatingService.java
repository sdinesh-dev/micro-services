package com.dinesh.moviecatalogservice.services;

import com.dinesh.moviecatalogservice.models.Rating;
import com.dinesh.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RatingService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(String userId){
        return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId, UserRating.class);
    }

    public UserRating getFallbackUserRating(String userId){
        return new UserRating(Arrays.asList(new Rating("100",0)));
    }
}
