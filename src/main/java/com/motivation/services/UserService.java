package com.motivation.services;

import com.motivation.models.viewModels.QuoteViewModel;

import java.util.List;

public interface UserService {

    Long getUserIdByUsername(String username);

    String getFullNameByUsername(String username);

    String getFullNameByUserId(Long userId);

    String getUsernameByUserId(Long userId);

    List<QuoteViewModel> findAllLikedQuotesByUserId(Long userId);
}
