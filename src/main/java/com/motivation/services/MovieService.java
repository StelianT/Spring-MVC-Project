package com.motivation.services;

import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddMovieBindingModel;
import com.motivation.models.viewModels.MovieViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    void save(AddMovieBindingModel addMovieBindingModel);

    List<MovieViewModel> findAllMovies();

    List<MovieViewModel> findAllMoviesByUserId(Long userId);

    Page<MovieViewModel> findAllMovies(Pageable pageable);

    void like(User user, long movieId);

    void unlike(User user, long movieId);

    AddMovieBindingModel getOneById(long movieId);

    void editMovie(AddMovieBindingModel addMovieBindingModel, long movieId);
}
