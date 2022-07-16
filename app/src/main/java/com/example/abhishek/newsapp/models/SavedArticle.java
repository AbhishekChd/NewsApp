package com.example.abhishek.newsapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(foreignKeys = @ForeignKey(
        entity = Article.class,
        parentColumns = "id",
        childColumns = "news_id"),
        indices = {@Index(value = "news_id", unique = true)},
        tableName = "saved"
)
public class SavedArticle {
    @ColumnInfo(name = "news_id")
    private final int newsId;
    @PrimaryKey
    @ColumnInfo(name = "time_saved")
    private Timestamp timestamp;

    public SavedArticle(int newsId) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.newsId = newsId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getNewsId() {
        return newsId;
    }
}
