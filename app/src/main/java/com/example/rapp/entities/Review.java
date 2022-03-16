package com.example.rapp.entities;

public class Review {
    private long ID;
    private String description;
    private long rating;

    private User user;
    private Recipe recipe;

    public Review() {
    }

    public Review(long ID, String description, long rating, User user, Recipe recipe) {
        this.ID = ID;
        this.description = description;
        this.rating = rating;
        this.user = user;
        this.recipe = recipe;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
