package com.motivation.models.bindingModels;

import com.motivation.entities.User;
import org.springframework.web.multipart.MultipartFile;

public class AddPictureBindingModel {

    private String title;

    private User addedBy;

    private MultipartFile picture;

    public AddPictureBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }
}
