package com.example.abhishek.newsapp.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(foreignKeys = @ForeignKey(
        entity = Article.class,
        parentColumns = "id",
        childColumns = "news_id"),
        tableName = "saved"
)
public class SavedArticle {
    @PrimaryKey
    @ColumnInfo(name = "time_saved")
    private Timestamp timestamp;
    @ColumnInfo(name = "news_id")
    private int newsId;

    public SavedArticle(int newsId) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.newsId = newsId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int getNewsId() {
        return newsId;
    }
}
