package com.motivation.controllers;

import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddLifeGoalBindingModel;
import com.motivation.models.viewModels.*;
import com.motivation.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserHomeController {

    private final QuoteService quoteService;
    private final UserService userService;
    private final PictureService pictureService;
    private final StoryService storyService;
    private final MovieService movieService;

    @Autowired
    public UserHomeController(QuoteService quoteService, UserService userService,
                              PictureService pictureService, StoryService storyService,
                              MovieService movieService) {
        this.quoteService = quoteService;
        this.userService = userService;
        this.pictureService = pictureService;
        this.storyService = storyService;
        this.movieService = movieService;
    }

    @GetMapping("")
    public String getCurrentUserPage(Model model, Principal principal,
                                     @ModelAttribute AddLifeGoalBindingModel addLifeGoalBindingModel){
        String username = principal.getName();
        Long userId = this.userService.getUserIdByUsername(username);
        getUserPage(userId, model);

        return "user";
    }

    @GetMapping("/{userId}")
    public String getUserPageByUserId(@PathVariable long userId, Model model) {
        getUserPage(userId, model);

        return "user";
    }

    private void getUserPage(long userId, Model model) {
        String username = this.userService.getUsernameByUserId(userId);
        String fullName = this.userService.getFullNameByUserId(userId);

        List<QuoteViewModel> addedQuotes = this.quoteService.findAllQuotesByUserId(userId);
        List<QuoteViewModel> likedQuotes = this.userService.findAllLikedQuotesByUserId(userId);
        List<PictureViewModel> addedPictures = this.pictureService.findAllPicturesByUserId(userId);
        List<PictureViewModel> likedPictures = this.userService.findAllLikedPicturesByUserId(userId);
        List<StoryViewModel> addedStories = this.storyService.findAllStoriesByUserId(userId);
        List<StoryViewModel> likedStories = this.userService.findAllLikedStoriesByUserId(userId);
        List<MovieViewModel> addedMovies = this.movieService.findAllMoviesByUserId(userId);
        List<MovieViewModel> likedMovies = this.userService.findAllLikedMoviesByUserId(userId);

        model.addAttribute("addedQuotes", addedQuotes);
        model.addAttribute("likedQuotes", likedQuotes);
        model.addAttribute("addedPictures", addedPictures);
        model.addAttribute("likedPictures", likedPictures);
        model.addAttribute("addedStories", addedStories);
        model.addAttribute("likedStories", likedStories);
        model.addAttribute("addedMovies", addedMovies);
        model.addAttribute("likedMovies", likedMovies);

        model.addAttribute("fullName", fullName);
        model.addAttribute("username", username);
    }
}
