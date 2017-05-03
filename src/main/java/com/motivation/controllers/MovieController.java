package com.motivation.controllers;

import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddMovieBindingModel;
import com.motivation.models.viewModels.MovieViewModel;
import com.motivation.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("")
    public String getMovieHomePage(Model model, @PageableDefault(size = 6) Pageable pageable) {

        Page<MovieViewModel> movies = this.movieService.findAllMovies(pageable);
        User currentUser = getCurrentUser();

        for (MovieViewModel movie : movies) {
            Set<User> likedBy = movie.getLikedBy();
            boolean isLikedByCurrentUser = false;
            for (User user : likedBy) {
                if (user.getUsername().equals(currentUser.getUsername())) {
                    isLikedByCurrentUser = true;
                }
            }
            movie.setLikedByCurrentUser(isLikedByCurrentUser);
        }
        model.addAttribute("movies", movies);

        return "movies";
    }

    @GetMapping("/add")
    public String getAddMoviePage(@ModelAttribute AddMovieBindingModel addMovieBindingModel) {
        return "movies-add";
    }

    @PostMapping("/add")
    public String addQuote(@ModelAttribute AddMovieBindingModel addMovieBindingModel) {

        addMovieBindingModel.setAddedBy(getCurrentUser());

        this.movieService.save(addMovieBindingModel);

        return "redirect:/movies";
    }

    @PostMapping("/like/{movieId}")
    public ResponseEntity likeQuote(@PathVariable long movieId) {
        this.movieService.like(getCurrentUser(), movieId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/unlike/{movieId}")
    public ResponseEntity unlikeQuote(@PathVariable long movieId) {
        this.movieService.unlike(getCurrentUser(), movieId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/edit/{movieId}")
    public String editMovie(@PathVariable long movieId, Model model) {
        AddMovieBindingModel movie = this.movieService.getOneById(movieId);
        model.addAttribute("addMovieBindingModel", movie);

        return "movie-edit";
    }

    @PostMapping("/edit/{movieId}")
    public String editMovie(@ModelAttribute AddMovieBindingModel addMovieBindingModel, @PathVariable long movieId) {
        if (this.movieService.getOneById(movieId).getAddedBy().getId() != getCurrentUser().getId()) {
            return "error/access-denied";
        }

        this.movieService.editMovie(addMovieBindingModel, movieId);

        return "redirect:/movies";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object myUser = (auth != null) ? auth.getPrincipal() :  null;

        return (User) myUser;
    }
}
