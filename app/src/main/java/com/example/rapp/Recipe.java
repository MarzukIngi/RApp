package com.example.rapp;

import java.util.List;

public class Recipe {
    private long ID;
    private String title;
    private String description;
    private List<String> ingredients;
    private boolean published;
    private long views;
    private double avgRating;

    // Á eftir að bæta inn Review og User klösunum
    //private List<Review> reviews;
    //private User user;

    public Recipe() {

    }

    public Recipe(String title, String description, List<String> ingredients, boolean published) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.published = published;
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
