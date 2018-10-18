package com.example.abhishek.newsapp.network;

import com.example.abhishek.newsapp.BuildConfig;
import com.example.abhishek.newsapp.models.ArticleResponseWrapper;
import com.example.abhishek.newsapp.models.SourceResponseWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NewsApi {
    String API_KEY = BuildConfig.NewsApiKey;

    @Headers("X-Api-Key:" + API_KEY)
    @GET("/v2/top-headlines")
    Call<ArticleResponseWrapper> getHeadlines(
            @Query("category") String category,
            @Query("country") String country,
            @Query("language") String language
    );

    @Headers("X-Api-Key:" + API_KEY)
    @GET("/v2/top-headlines")
    Call<ArticleResponseWrapper> getHeadlinesBySource(
            @Query("sources") String source,
            @Query("language") String language
    );

    @Headers("X-Api-Key:" + API_KEY)
    @GET("/v2/sources")
    Call<SourceResponseWrapper> getSources(
            @Query("category") String category,
            @Query("country") String country,
            @Query("language") String language
    );

    enum Category {
        business,
        entertainment,
        general,
        health,
        science,
        sports,
        technology
    }
}