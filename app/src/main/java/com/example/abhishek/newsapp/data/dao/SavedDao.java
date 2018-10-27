package com.example.abhishek.newsapp.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.abhishek.newsapp.models.Article;
import com.example.abhishek.newsapp.models.SavedArticle;

import java.util.List;

@Dao
public interface SavedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SavedArticle article);

    @Query("SELECT COUNT(news_id) > 0 FROM saved WHERE news_id = :articleId")
    LiveData<Boolean> isFavourite(int articleId);

    @Query("DELETE FROM saved WHERE news_id=:articleId")
    LiveData<Boolean> removeSaved(int articleId);

    @Query("SELECT articles.* FROM articles, saved " +
            "WHERE articles.id == saved.news_id " +
            "ORDER BY saved.time_saved")
    LiveData<List<Article>> getAllSaved();
}
