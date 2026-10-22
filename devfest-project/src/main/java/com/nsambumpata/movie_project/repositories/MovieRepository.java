package com.nsambumpata.movie_project.repositories;

import com.nsambumpata.movie_project.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
