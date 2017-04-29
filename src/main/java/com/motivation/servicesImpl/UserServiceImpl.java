package com.motivation.servicesImpl;

import com.motivation.entities.Picture;
import com.motivation.entities.Quote;
import com.motivation.entities.User;
import com.motivation.models.viewModels.PictureViewModel;
import com.motivation.models.viewModels.QuoteViewModel;
import com.motivation.repository.AbstractUserRepository;
import com.motivation.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

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
}
