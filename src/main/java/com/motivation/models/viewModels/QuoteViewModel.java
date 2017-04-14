package com.motivation.models.viewModels;

import com.motivation.entities.User;

import java.util.Set;

public class QuoteViewModel {

    private String content;

    private String author;

    private String color;

    private String addedByUsername;

    private Set<String> userIdUsername;

    public QuoteViewModel() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAddedByUsername() {
        return addedByUsername;
    }

    public void setAddedByUsername(String addedByUsername) {
        this.addedByUsername = addedByUsername;
    }

    public Set<String> getUserIdUsername() {
        return userIdUsername;
    }

    public void setUserIdUsername(Set<String> userIdUsername) {
        this.userIdUsername = userIdUsername;
    }
}
