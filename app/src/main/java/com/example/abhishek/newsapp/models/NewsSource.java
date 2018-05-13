package com.example.abhishek.newsapp.models;

public class NewsSource {
    private String id;
    private String name;

    /**
     * NewsSource of the news
     *
     * @param id   id of the news source, example cnn
     * @param name display name of news source Example CNN
     */
    public NewsSource(String id, String name) {
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
