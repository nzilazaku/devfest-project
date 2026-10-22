package com.nsambumpata.movie_project.controllers;

import com.nsambumpata.movie_project.models.Movie;
import com.nsambumpata.movie_project.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @CrossOrigin
    @GetMapping
    public List<Movie> getMovies(){
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") Long id){
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") Long id){
        if (!movieRepository.existsById(id)){
            ResponseEntity.notFound().build();
        }

        movieRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<Movie> updateMovie(@PathVariable("id") Long id, @RequestBody Movie updatedMovie){
        if (!movieRepository.existsById(id)){
            ResponseEntity.notFound().build();
        }
        updatedMovie.setId(id);
        Movie savedMovie =  movieRepository.save(updatedMovie);
        return ResponseEntity.ok(savedMovie);
    }

    @CrossOrigin
    @GetMapping("/votes/{id}/{rating}")
    public ResponseEntity<Movie> voteMovie(@PathVariable("id") Long id, @RequestBody double rating){
        if (!movieRepository.existsById(id)){
            ResponseEntity.notFound().build();
        }

        Optional<Movie> movieSearched = movieRepository.findById(id);
        Movie movie = movieSearched.get();

        double newRating = ((movie.getVotes() + movie.getRating() + rating)) / movie.getVotes() + 1;

        movie.setVotes(movie.getVotes() + 1);
        movie.setRating(newRating);

        Movie savedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(savedMovie);

    }

}
