package com.motivation.servicesImpl;

import com.motivation.entities.Picture;
import com.motivation.models.bindingModels.AddPictureBindingModel;
import com.motivation.models.viewModels.PictureViewModel;
import com.motivation.repository.PictureRepository;
import com.motivation.services.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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

        this.pictureRepository.save(picture);
    }

    @Override
    public List<PictureViewModel> findAllPictures() {
        List<PictureViewModel> models = new ArrayList<>();
        List<Picture> pictures = this.pictureRepository.findAll();
        for (Picture picture : pictures) {
            //PictureViewModel model = this.modelMapper.map(picture, PictureViewModel.class);
            PictureViewModel model = new PictureViewModel();
            model.setTitle(picture.getTitle());
            String encodedPicture = Base64.getEncoder().encodeToString(picture.getPicture());
            model.setPicture(encodedPicture);
            models.add(model);
        }

        return models;
    }
}
