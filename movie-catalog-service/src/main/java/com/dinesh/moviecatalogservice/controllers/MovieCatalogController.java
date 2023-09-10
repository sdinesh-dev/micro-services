package com.dinesh.moviecatalogservice.controllers;

import com.dinesh.moviecatalogservice.models.CatalogItem;
import com.dinesh.moviecatalogservice.models.Movie;
import com.dinesh.moviecatalogservice.models.Rating;
import com.dinesh.moviecatalogservice.models.UserRating;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        UserRating userRating = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId, UserRating.class);
         return userRating.getRatingList().stream().map(
                rating -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(),movie.getDescription(),rating.getRating());
                })
                .collect(Collectors.toList());
    }
@RequestMapping("webclient/{userId}")
    public List<CatalogItem> getCatalogUsingWebClient(@PathVariable("userId") String userId){
        List<Rating> ratingList = Arrays.asList(
                new Rating("1234",3),
                new Rating("5678",4)
        );

        return ratingList.stream().map(
                        rating -> {
                            Movie movie= webClientBuilder
                                    .build()
                                    .get()
                                    .uri("http://localhost:8082/movies/"+rating.getMovieId())
                                    .retrieve()
                                    .bodyToMono(Movie.class)
                                    .block();
                            return new CatalogItem(movie.getName(),"Desc",rating.getRating());
                        })
                .collect(Collectors.toList());
    }
}
