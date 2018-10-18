package com.example.abhishek.newsapp.models;

/**
 * Source of news provided
 */
public class Source {
    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;

    /**
     * @param id          of the news source, example <b>cnn</b>
     * @param name        display name of news source, example <b>CNN</b>
     * @param description a description of the news source
     * @param url         URL of the homepage
     * @param category    type of news to expect from this news source, example <b>general</b>
     * @param language    language that this news source writes in, example <b>en</b>
     * @param country     country this news source is based in (and primarily writes about), example <b>au</b>
     */
    public Source(String id, String name, String description, String url, String category, String language, String country) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.language = language;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }
}
