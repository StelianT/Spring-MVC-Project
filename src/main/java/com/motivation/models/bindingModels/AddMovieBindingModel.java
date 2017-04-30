package com.motivation.models.bindingModels;

import com.motivation.entities.User;

public class AddMovieBindingModel {

    private String title;

    private User addedBy;

    private String description;

    public AddMovieBindingModel() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
