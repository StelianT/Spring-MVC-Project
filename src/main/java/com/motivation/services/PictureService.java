package com.motivation.services;

import com.motivation.models.bindingModels.AddPictureBindingModel;
import com.motivation.models.viewModels.PictureViewModel;

import java.util.List;

public interface PictureService {

    void save(AddPictureBindingModel addPictureBindingModel);

    List<PictureViewModel> findAllPictures();
}
