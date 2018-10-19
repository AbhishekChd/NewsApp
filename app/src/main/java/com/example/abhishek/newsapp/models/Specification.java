package com.example.abhishek.newsapp.models;

import com.example.abhishek.newsapp.network.NewsApi;

public class Specification {
    private String category;
    private String country;
    private String language;

    public String getCategory() {
        return category;
    }

    public void setCategory(NewsApi.Category category) {
        this.category = category.name();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language == null ? "en" : language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
