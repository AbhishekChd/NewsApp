package com.example.abhishek.newsapp.models;

import java.util.List;

/**
 * Response of network requests for News Articles
 */
public class ArticleResponseWrapper {
    private String status;
    private int totalResults;
    private List<Article> articles;

    /**
     * @param status       If the request was successful or not. Options: ok, error.
     * @param totalResults The total number of results available for your request.
     * @param articles     The results of the request.
     */
    public ArticleResponseWrapper(String status, int totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
