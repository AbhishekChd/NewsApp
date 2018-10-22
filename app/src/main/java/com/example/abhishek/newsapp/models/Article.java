package com.example.abhishek.newsapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.sql.Timestamp;

/**
 * A News Article content and it's details
 * Along with the {@link ArticleSource} of News Article
 */
@Entity(tableName = "articles", indices = {@Index(value = "title", unique = true)})
public class Article {
    @PrimaryKey(autoGenerate = true)
    @Expose(serialize = false, deserialize = false)
    public int id;
    @ColumnInfo(name = "author")
    private final String author;
    @ColumnInfo(name = "title")
    private final String title;
    @ColumnInfo(name = "description")
    private final String description;
    @ColumnInfo(name = "url")
    private final String url;
    @ColumnInfo(name = "published_at")
    private final Timestamp publishedAt;
    @ColumnInfo(name = "image_url")
    private final String urlToImage;
    @Embedded(prefix = "source_")
    private final ArticleSource source;
    @ColumnInfo(name = "content")
    private final String content;
    @ColumnInfo(name = "category")
    @Expose(serialize = false, deserialize = false)
    private String category;
    @ColumnInfo(name = "save_date")
    @Expose(serialize = false, deserialize = false)
    private Timestamp saveDate = new Timestamp(System.currentTimeMillis());

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public Timestamp getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Timestamp saveDate) {
        this.saveDate = saveDate;
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

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", publishedAt=" + publishedAt +
                ", urlToImage='" + urlToImage + '\'' +
                ", source=" + source +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", saveDate=" + saveDate +
                '}';
    }
}
