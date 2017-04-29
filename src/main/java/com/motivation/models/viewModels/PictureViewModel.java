package com.motivation.models.viewModels;


import com.motivation.entities.User;

import java.util.Set;

public class PictureViewModel {

    private Long id;

    private String picture;

    private String title;

    private String addedByUsername;

    private Set<User> likedBy;

    private boolean isLikedByCurrentUser;

    public PictureViewModel() {
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddedByUsername() {
        return addedByUsername;
    }

    public void setAddedByUsername(String addedByUsername) {
        this.addedByUsername = addedByUsername;
    }

    public Set<User> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<User> likedBy) {
        this.likedBy = likedBy;
    }

    public boolean getIsLikedByCurrentUser() {
        return isLikedByCurrentUser;
    }

    public void setLikedByCurrentUser(boolean likedByCurrentUser) {
        isLikedByCurrentUser = likedByCurrentUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
