package com.dinesh.moviecatalogservice.services;

import com.dinesh.moviecatalogservice.models.Rating;
import com.dinesh.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RatingService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
            commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
            },
            threadPoolKey = "ratingInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value = "20"),
                    @HystrixProperty(name="maxQueueSize", value = "10")
            })
    public UserRating getUserRating(String userId){
        return restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId, UserRating.class);
    }

    public UserRating getFallbackUserRating(String userId){
        return new UserRating(Arrays.asList(new Rating("100",0)));
    }
}
