package com.motivation.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    private String author;

    private String color;

    private User addedBy;

//    private Set<User> likedBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

//    public Set<User> getLikedBy() {
//        return likedBy;
//    }
//
//    public void setLikedBy(Set<User> likedBy) {
//        this.likedBy = likedBy;
//    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
