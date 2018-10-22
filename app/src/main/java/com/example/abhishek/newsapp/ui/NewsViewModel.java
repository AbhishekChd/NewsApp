package com.example.abhishek.newsapp.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.abhishek.newsapp.data.NewsRepository;
import com.example.abhishek.newsapp.models.Article;
import com.example.abhishek.newsapp.models.Specification;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private final NewsRepository mNewsRepository;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        mNewsRepository = NewsRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Article>> getNewsHeadlines(Specification specification) {
        return mNewsRepository.getHeadlines(specification);
    }
}
