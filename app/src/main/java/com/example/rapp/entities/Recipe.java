package com.example.rapp.entities;

import java.util.List;

public class Recipe {
    private long ID;
    private String title;
    private String description;
    private List<String> ingredients;
    private boolean published;
    private long views;
    private double avgRating;
    private List<Review> reviews;
    private User user;
    private Page page;

    public Recipe() {

    }

    public Recipe(long ID, String title, String description, List<String> ingredients, boolean published, long views, double avgRating, List<Review> reviews, User user, Page page) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.published = published;
        this.views = views;
        this.avgRating = avgRating;
        this.reviews = reviews;
        this.user = user;
        this.page = page;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }
}
