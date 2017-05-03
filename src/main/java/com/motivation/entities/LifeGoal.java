package com.motivation.entities;

import javax.persistence.*;

@Entity
public class LifeGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;

    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable=false)
    private User user;

    public LifeGoal() {
    }

    public LifeGoal(String content, boolean isCompleted, User user) {
        this.content = content;
        this.isCompleted = isCompleted;
        this.user = user;
    }

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

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
