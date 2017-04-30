package com.motivation.models.bindingModels;

import com.motivation.entities.User;

public class AddStoryBindingModel {

    private String person;

    private String content;

    private User addedBy;

    public AddStoryBindingModel() {
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }
}
