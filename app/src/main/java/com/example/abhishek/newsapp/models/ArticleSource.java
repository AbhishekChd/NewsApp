package com.example.abhishek.newsapp.models;

/**
 * Source of the news article
 * A minimized version of {@link Source} returned with {@link Article}
 * Only the mandatory details are included
 */
public class ArticleSource {
    private String id;
    private String name;

    /**
     * @param id   id of the news source, example <b>cnn</b>
     * @param name display name of news source, example <b>CNN</b>
     */
    public ArticleSource(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
