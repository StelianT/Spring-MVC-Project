package com.motivation.models.bindingModels;

import com.motivation.entities.User;

public class AddLifeGoalBindingModel {

    private String content;

    private User user;

    public AddLifeGoalBindingModel() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
