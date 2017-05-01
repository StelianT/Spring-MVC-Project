package com.motivation.services;

import com.motivation.models.viewModels.MovieViewModel;
import com.motivation.models.viewModels.PictureViewModel;
import com.motivation.models.viewModels.QuoteViewModel;
import com.motivation.models.viewModels.StoryViewModel;

import java.util.List;

public interface UserService {

    Long getUserIdByUsername(String username);

    String getFullNameByUsername(String username);

    String getFullNameByUserId(Long userId);

    String getUsernameByUserId(Long userId);

    List<QuoteViewModel> findAllLikedQuotesByUserId(Long userId);

    List<PictureViewModel> findAllLikedPicturesByUserId(Long userId);

    List<StoryViewModel> findAllLikedStoriesByUserId(Long userId);

    List<MovieViewModel> findAllLikedMoviesByUserId(Long userId);
}
