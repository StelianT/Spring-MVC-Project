package com.motivation.servicesImpl;

import com.motivation.entities.Picture;
import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddPictureBindingModel;
import com.motivation.models.viewModels.PictureViewModel;
import com.motivation.repository.PictureRepository;
import com.motivation.services.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(AddPictureBindingModel addPictureBindingModel) {
        //Picture picture = this.modelMapper.map(addPictureBindingModel, Picture.class);

        Picture picture = new Picture();
        picture.setTitle(addPictureBindingModel.getTitle());
        try {
            picture.setPicture(addPictureBindingModel.getPicture().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        picture.setAddedBy(addPictureBindingModel.getAddedBy());

        this.pictureRepository.save(picture);
    }

    @Override
    public List<PictureViewModel> findAllPictures() {
        List<PictureViewModel> models = new ArrayList<>();
        List<Picture> pictures = this.pictureRepository.findAll();
        for (Picture picture : pictures) {
            //PictureViewModel model = this.modelMapper.map(picture, PictureViewModel.class);
            models.add(PictureViewModelMapper(picture));
        }

        return models;
    }

    @Override
    public Page<PictureViewModel> findAllPictures(Pageable pageable) {
        Page<Picture> pictures = this.pictureRepository.findAll(pageable);
        List<PictureViewModel> pictureViewModels = new ArrayList<>();
        for (Picture picture : pictures) {
            pictureViewModels.add(PictureViewModelMapper(picture));
        }

        Page<PictureViewModel> pictureModels = new PageImpl<>(pictureViewModels, pageable,
                pictures.getTotalElements());
        return pictureModels;
    }

    @Override
    public AddPictureBindingModel getOneById(long pictureId) {
        Picture picture =  this.pictureRepository.getOne(pictureId);
        return this.modelMapper.map(picture, AddPictureBindingModel.class);
    }

    @Override
    public void editPicture(AddPictureBindingModel addPictureBindingModel, long pictureId) {
        Picture picture = this.pictureRepository.getOne(pictureId);
        try {
            picture.setPicture(addPictureBindingModel.getPicture().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        picture.setTitle(addPictureBindingModel.getTitle());
        this.pictureRepository.save(picture);
    }

    @Override
    public void like(User user, long pictureId) {
        Picture picture = this.pictureRepository.findOneById(pictureId);
        picture.getLikedBy().add(user);
        this.pictureRepository.saveAndFlush(picture);
    }

    @Override
    public void unlike(User user, long pictureId) {
        Set<User> usersLikedPicture = this.pictureRepository.findOneById(pictureId).getLikedBy();
        for (User userInCollection : usersLikedPicture) {
            if (userInCollection.getUsername().equals(user.getUsername())) {
                usersLikedPicture.remove(userInCollection);
                this.pictureRepository.saveAndFlush(this.pictureRepository.findOneById(pictureId));
                return;
            }
        }
    }

    @Override
    public List<PictureViewModel> findAllPicturesByUserId(Long userId) {
        List<PictureViewModel> models = new ArrayList<>();
        List<Picture> pictures = this.pictureRepository.findAllByAddedById(userId);
        for (Picture picture : pictures) {
            //PictureViewModel model = this.modelMapper.map(picture, PictureViewModel.class);
            models.add(PictureViewModelMapper(picture));
        }

        return models;
    }

    private PictureViewModel PictureViewModelMapper(Picture picture) {

        PictureViewModel model = new PictureViewModel();
        model.setTitle(picture.getTitle());
        String encodedPicture = Base64.getEncoder().encodeToString(picture.getPicture());
        model.setPicture(encodedPicture);
        model.setAddedByUsername(picture.getAddedBy().getUsername());
        model.setLikedBy(picture.getLikedBy());
        model.setId(picture.getId());

        return model;
    }
}
