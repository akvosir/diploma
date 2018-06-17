package com.example.yuliia.diploma.models;

public class ActivityUser {
    private int liked;
    private int rating;

    public int getLiked() {
        return liked;
    }

    public int getRating() {
        return rating;
    }

    public ActivityUser(int liked, int rating) {
        this.liked = liked;
        this.rating = rating;
    }
}
