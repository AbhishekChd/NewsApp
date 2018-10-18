package com.example.abhishek.newsapp.old.models;

import java.util.ArrayList;
import java.util.List;

public class Box {
    private String status;

    @Override
    public String toString() {
        return "Box{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                '}';
    }

    private int totalResults;
    private List<NewsArticle> articles = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }
}
