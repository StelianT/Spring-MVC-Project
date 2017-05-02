package com.motivation.servicesImpl;

import com.motivation.entities.*;
import com.motivation.models.viewModels.MovieViewModel;
import com.motivation.models.viewModels.PictureViewModel;
import com.motivation.models.viewModels.QuoteViewModel;
import com.motivation.models.viewModels.StoryViewModel;
import com.motivation.repository.AbstractUserRepository;
import com.motivation.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements com.motivation.services.UserService {

    @Autowired
    private AbstractUserRepository abstractUserRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Long getUserIdByUsername(String username) {
        User user = this.abstractUserRepository.findOneByUsername(username);
        return user.getId();
    }

    @Override
    public String getFullNameByUsername(String username) {
        User user = this.abstractUserRepository.findOneByUsername(username);
        return user.getFullName();
    }

    @Override
    public String getFullNameByUserId(Long userId) {
        User user = this.abstractUserRepository.findOne(userId);
        return user.getFullName();
    }

    @Override
    public String getUsernameByUserId(Long userId) {
        User user = this.abstractUserRepository.findOne(userId);
        return user.getUsername();
    }

    @Override
    public List<QuoteViewModel> findAllLikedQuotesByUserId(Long userId) {
        User user = this.abstractUserRepository.findOne(userId);
        Set<Quote> likedQuotes = user.getLikedQuotes();
        List<QuoteViewModel> models = new ArrayList<>();
        for (Quote quote : likedQuotes) {
            QuoteViewModel quoteViewModel = this.modelMapper.map(quote, QuoteViewModel.class);
            models.add(quoteViewModel);
        }

        return models;
    }

    @Override
    public List<PictureViewModel> findAllLikedPicturesByUserId(Long userId) {
        User user = this.abstractUserRepository.findOne(userId);
        Set<Picture> likedPictures = user.getLikedPictures();
        List<PictureViewModel> models = new ArrayList<>();
        for (Picture picture : likedPictures) {
            PictureViewModel model = new PictureViewModel();
            model.setTitle(picture.getTitle());
            String encodedPicture = Base64.getEncoder().encodeToString(picture.getPicture());
            model.setPicture(encodedPicture);
            model.setAddedByUsername(picture.getAddedBy().getUsername());
            model.setLikedBy(picture.getLikedBy());
            model.setId(picture.getId());

            models.add(model);
        }

        return models;
    }

    @Override
    public List<StoryViewModel> findAllLikedStoriesByUserId(Long userId) {
        User user = this.abstractUserRepository.findOne(userId);
        Set<Story> likedStories = user.getLikedStories();
        List<StoryViewModel> models = new ArrayList<>();
        for (Story story : likedStories) {
            StoryViewModel storyViewModel = this.modelMapper.map(story, StoryViewModel.class);
            models.add(storyViewModel);
        }

        return models;
    }

    @Override
    public List<MovieViewModel> findAllLikedMoviesByUserId(Long userId) {
        User user = this.abstractUserRepository.findOne(userId);
        Set<Movie> likedMovies = user.getLikedMovies();
        List<MovieViewModel> models = new ArrayList<>();
        for (Movie movie : likedMovies) {
            MovieViewModel movieViewModel = this.modelMapper.map(movie, MovieViewModel.class);
            models.add(movieViewModel);
        }

        return models;
    }
}
