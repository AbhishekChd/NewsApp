package com.example.abhishek.newsapp.old.models;

/**
 * A {@link NewsPost} object contain information about a single News Post
 */
public class NewsPost {
    private String title;
    private String description;
    private String source;
    private String imageUrl;
    private String postUrl;

    public NewsPost(String title, String description, String source, String imageUrl, String postUrl) {
        this.title = title;
        this.description = description;
        this.source = source;
        this.imageUrl = imageUrl;
        this.postUrl = postUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public String getSource() {
        return source;
    }
}
