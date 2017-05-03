package com.motivation.services;

import com.motivation.entities.User;
import com.motivation.models.bindingModels.AddPictureBindingModel;
import com.motivation.models.viewModels.PictureViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PictureService {

    void save(AddPictureBindingModel addPictureBindingModel);

    List<PictureViewModel> findAllPictures();

    void like(User user, long pictureId);

    void unlike(User user, long pictureId);

    List<PictureViewModel> findAllPicturesByUserId(Long userId);

    Page<PictureViewModel> findAllPictures(Pageable pageable);

    AddPictureBindingModel getOneById(long pictureId);

    void editPicture(AddPictureBindingModel addPictureBindingModel, long pictureId);
}
