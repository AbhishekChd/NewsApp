package com.example.abhishek.newsapp.ui.news;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.example.abhishek.newsapp.data.NewsRepository;
import com.example.abhishek.newsapp.models.Article;
import com.example.abhishek.newsapp.models.Specification;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private final NewsRepository newsRepository;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = NewsRepository.getInstance(application.getApplicationContext());
    }

    public LiveData<List<Article>> getNewsHeadlines(Specification specification) {
        return newsRepository.getHeadlines(specification);
    }

    public LiveData<List<Article>> getAllSaved() {
        return newsRepository.getSaved();
    }

    public LiveData<Boolean> isSaved(int articleId) {
        return newsRepository.isSaved(articleId);
    }

    public void toggleSave(int articleId) {
        newsRepository.removeSaved(articleId);
    }
}
