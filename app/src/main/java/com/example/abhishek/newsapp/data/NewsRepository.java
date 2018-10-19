package com.example.abhishek.newsapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.abhishek.newsapp.models.Article;
import com.example.abhishek.newsapp.models.ArticleResponseWrapper;
import com.example.abhishek.newsapp.models.Specification;
import com.example.abhishek.newsapp.network.NewsApi;
import com.example.abhishek.newsapp.network.NewsApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private static final Object LOCK = new Object();
    private static NewsRepository sInstance;

    private final NewsApi newsApiService;
    private final HeadlinesDao headlinesDao;
    private final SourcesDao sourceDao;
    private MutableLiveData<List<Article>> articlesLiveData;

    // required private constructor for Singleton pattern
    private NewsRepository(Context context) {
        newsApiService = NewsApiClient.getInstance(context);
        headlinesDao = NewsDatabase.getInstance(context).headlinesDao();
        sourceDao = NewsDatabase.getInstance(context).sourcesDao();
    }

    public synchronized static NewsRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NewsRepository(context);
            }
        }
        return sInstance;
    }

    public LiveData<List<Article>> getHeadlines(Specification specs) {
        Call<ArticleResponseWrapper> networkCall = newsApiService.getHeadlines(
                specs.getCategory(),
                specs.getCountry(),
                specs.getLanguage()
        );

        networkCall.enqueue(new Callback<ArticleResponseWrapper>() {
            @Override
            public void onResponse(Call<ArticleResponseWrapper> call, Response<ArticleResponseWrapper> response) {
                if (response.body() != null) {
                    articlesLiveData.setValue(response.body().getArticles());
                }
            }

            @Override
            public void onFailure(Call<ArticleResponseWrapper> call, Throwable t) {

            }
        });
        return articlesLiveData;
    }
}
