package com.example.abhishek.newsapp.models;

import java.sql.Timestamp;

/**
 * A News Article content and it's details
 * Along with the {@link ArticleSource} of News Article
 */
public class Article {
    private String author;
    private String title;
    private String description;
    private String url;
    private Timestamp publishedAt;
    private String urlToImage;
    private ArticleSource source;
    private String content;

    /**
     * @param author      author of the article
     * @param title       headline or title of the article.
     * @param description description or snippet from the article.
     * @param url         The direct URL to the article
     * @param publishedAt The date and time that the article was published, in UTC (+000)
     * @param urlToImage  The URL to a relevant image for the article.
     * @param source      The identifier id and a display name name for the source this article came from.
     * @param content     The unformatted content of the article, where available. This is truncated to
     *                    260 chars for Developer plan users.
     */
    public Article(String author, String title, String description, String url, Timestamp publishedAt, String urlToImage, ArticleSource source, String content) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.publishedAt = publishedAt;
        this.urlToImage = urlToImage;
        this.source = source;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public Timestamp getPublishedAt() {
        return publishedAt;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public ArticleSource getSource() {
        return source;
    }

    public String getContent() {
        return content;
    }
}
