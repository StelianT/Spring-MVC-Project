package com.motivation.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullName;

    private String username;

    private String password;

    @OneToMany(mappedBy = "addedBy")
    private Set<Quote> quotes;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy="likedBy", fetch = FetchType.EAGER)
    private Set<Quote> likedQuotes;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy="likedBy", fetch = FetchType.EAGER)
    private Set<Picture> likedPictures;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy="likedBy", fetch = FetchType.EAGER)
    private Set<Story> likedStories;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy="likedBy", fetch = FetchType.EAGER)
    private Set<Movie> likedMovies;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities;

    public User() {
        this.quotes = new HashSet<>();
        this.likedQuotes = new HashSet<>();
        this.likedPictures = new HashSet<>();
        this.likedStories = new HashSet<>();
        this.likedMovies = new HashSet<>();
    }

    @Override
    public Set<Role> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(Set<Quote> quotes) {
        this.quotes = quotes;
    }

    public Set<Quote> getLikedQuotes() {
        return likedQuotes;
    }

    public void setLikedQuotes(Set<Quote> likedQuotes) {
        this.likedQuotes = likedQuotes;
    }

    public Set<Picture> getLikedPictures() {
        return likedPictures;
    }

    public void setLikedPictures(Set<Picture> likedPictures) {
        this.likedPictures = likedPictures;
    }

    public Set<Story> getLikedStories() {
        return likedStories;
    }

    public void setLikedStories(Set<Story> likedStories) {
        this.likedStories = likedStories;
    }

    public Set<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(Set<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }
}
