package com.motivation.repository;

import com.motivation.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "SELECT q FROM Quote AS q")
    List<Movie> findAll();

    List<Movie> findAllByAddedById(Long userId);

    Movie findOneById(Long movieId);
}
