package com.motivation.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] picture;

    @ManyToOne
    @JoinColumn(name = "added_by_id", updatable=false)
    private User addedBy;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "pictures_users",
            joinColumns = @JoinColumn(name = "picture_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> likedBy;

    public Picture() {
        this.likedBy = new HashSet<>();
    }

    public Picture(String title, byte[] picture, User addedBy) {
        this();
        this.title = title;
        this.picture = picture;
        this.addedBy = addedBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public Set<User> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<User> likedBy) {
        this.likedBy = likedBy;
    }
}
