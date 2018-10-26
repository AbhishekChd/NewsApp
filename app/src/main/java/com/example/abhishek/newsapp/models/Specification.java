package com.example.abhishek.newsapp.models;

import com.example.abhishek.newsapp.network.NewsApi;

import java.util.Locale;

public class Specification {
    // List of available countries
    private static final String[] COUNTRIES_AVAILABLE = {"ae", "ar", "at", "au", "be", "bg", "br",
            "ca", "ch", "cn", "co", "cu", "cz", "de", "eg", "fr", "gb", "gr", "hk", "hu", "id",
            "ie", "il", "in", "it", "jp", "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no",
            "nz", "ph", "pl", "pt", "ro", "rs", "ru", "sa", "se", "sg", "si", "sk", "th", "tr",
            "tw", "ua", "us", "ve", "za"};

    private String category;
    // Default country
    private String country = Locale.getDefault().getCountry().toLowerCase();
    private String language = null;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

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
}
