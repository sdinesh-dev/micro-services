package com.dinesh.moviecatalogservice.controllers;

import com.dinesh.moviecatalogservice.models.CatalogItem;
import com.dinesh.moviecatalogservice.models.Movie;
import com.dinesh.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        List<Rating> ratingList = Arrays.asList(
                new Rating("1234",3),
                new Rating("5678",4)
        );
         return ratingList.stream().map(
                rating -> {
                    Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(),"Desc",rating.getRating());
                })
                .collect(Collectors.toList());
    }
}
