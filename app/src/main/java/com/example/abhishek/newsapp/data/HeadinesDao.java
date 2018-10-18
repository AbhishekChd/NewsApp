package com.example.abhishek.newsapp.data;

import android.arch.persistence.room.Insert;

import com.example.abhishek.newsapp.models.Article;

import java.util.List;

public interface HeadinesDao {
    @Insert
    void bulkInsert(List<Article> articles);
}
