package com.dinesh.moviecatalogservice.services;

import com.dinesh.moviecatalogservice.models.Movie;
import com.dinesh.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackMovie")
    public Movie getMovie(Rating rating){
        return restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
    }

    public Movie getFallbackMovie(Rating rating){
        return new Movie("MovieId","Movie name not found","Movie Description Not Found");
    }
}
