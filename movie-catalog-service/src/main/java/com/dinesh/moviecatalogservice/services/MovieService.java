package com.dinesh.moviecatalogservice.services;

import com.dinesh.moviecatalogservice.models.Movie;
import com.dinesh.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackMovie",
            commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000"),
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
    })
    public Movie getMovie(Rating rating){
        return restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
    }
    public Movie getFallbackMovie(Rating rating){
        return new Movie("MovieId","Movie name not found","Movie Description Not Found");
    }
}
