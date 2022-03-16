package com.example.rapp.entities;

public class Following {
    private long followedUserId;
    private User follower;

    public Following() {}

    public Following(long followedUserId, User follower) {
        this.followedUserId = followedUserId;
        this.follower = follower;
    }

    public long getFollowedUserId() {
        return followedUserId;
    }

    public void setFollowedUserId(long followedUserId) {
        this.followedUserId = followedUserId;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }
}
