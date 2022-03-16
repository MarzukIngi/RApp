package com.example.rapp.entities;

import java.util.List;

public class User {
    private long ID;
    private String userName;
    private String email;
    private String password;
    private boolean admin;
    private List<Review> reviews;
    private List<Page> pages;
    private List<Following> following;


    public User() {
    }

    public User(long ID, String userName, String email, String password, boolean admin, List<Review> reviews, List<Page> pages, List<Following> following) {
        this.ID = ID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.reviews = reviews;
        this.pages = pages;
        this.following = following;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public List<Following> getFollowing() {
        return following;
    }

    public void setFollowing(List<Following> following) {
        this.following = following;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
