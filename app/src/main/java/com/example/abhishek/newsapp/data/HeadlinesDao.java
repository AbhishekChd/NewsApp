package com.example.abhishek.newsapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.abhishek.newsapp.models.Article;

import java.util.List;

public interface HeadlinesDao {
    @Insert
    void bulkInsert(List<Article> articles);

    @Query("SELECT * FROM articles")
    LiveData<List<Article>> getAllArticles();

    @Query("SELECT * FROM articles WHERE category=:category")
    LiveData<List<Article>> getArticleByCategory(String category);
}
