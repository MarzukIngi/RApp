package com.example.rapp.entities;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Page {
    @SerializedName("id")
    private long ID;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("user")
    private User user;
    @SerializedName("collaborators")
    private List<User> collaborators;
    @SerializedName("recipes")
    private List<Recipe> recipes;
    @SerializedName("followings")
    private List<Following> followings;

    public Page() {
    }

    public Page(long ID, String title, String description) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        //this.user = user;
        //this.collaborators = collaborators;
        //this.recipes = recipes;
        //this.followings = followings;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<User> collaborators) {
        this.collaborators = collaborators;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Following> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Following> followings) {
        this.followings = followings;
    }
}
