package com.example.abhishek.newsapp.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.abhishek.newsapp.models.Article;
import com.example.abhishek.newsapp.models.ArticleResponseWrapper;
import com.example.abhishek.newsapp.models.Specification;
import com.example.abhishek.newsapp.utils.DateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * A Singleton client class that provides {@link NewsApi} instance to load network requests
 */
public class NewsApiClient {
    private static final String NEWS_API_URL = "https://newsapi.org/";
    private static final Object LOCK = new Object();
    private static NewsApi sNewsApi;
    private static NewsApiClient sInstance;

    // Required private constructor
    private NewsApiClient() {
    }

    /**
     * Provides instance of {@link NewsApi}
     *
     * @param context Context of current Activity or Application
     * @return {@link NewsApi}
     */
    public static NewsApiClient getInstance(Context context) {
        if (sInstance == null || sNewsApi == null) {
            synchronized (LOCK) {
                // 5 MB of cache
                Cache cache = new Cache(context.getApplicationContext().getCacheDir(), 5 * 1024 * 1024);

                // Used for cache connection
                Interceptor networkInterceptor = new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        // set max-age and max-stale properties for cache header
                        CacheControl cacheControl = new CacheControl.Builder()
                                .maxAge(1, TimeUnit.HOURS)
                                .maxStale(3, TimeUnit.DAYS)
                                .build();
                        return chain.proceed(chain.request())
                                .newBuilder()
                                .removeHeader("Pragma")
                                .header("Cache-Control", cacheControl.toString())
                                .build();
                    }
                };

                // For logging
                HttpLoggingInterceptor loggingInterceptor =
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);


                // Building OkHttp client
                OkHttpClient client = new OkHttpClient.Builder()
                        .cache(cache)
                        .addNetworkInterceptor(networkInterceptor)
                        .addInterceptor(loggingInterceptor)
                        .build();

                // Configure GSON
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Date.class, new DateDeserializer())
                        .create();

                // Retrofit Builder
                Retrofit.Builder builder =
                        new Retrofit
                                .Builder()
                                .baseUrl(NEWS_API_URL)
                                .client(client)
                                .addConverterFactory(GsonConverterFactory.create(gson));
                // Set NewsApi instance
                sNewsApi = builder.build().create(NewsApi.class);
                sInstance = new NewsApiClient();
            }
        }
        return sInstance;
    }

    public LiveData<List<Article>> getHeadlines(final Specification specs) {
        final MutableLiveData<List<Article>> networkArticleLiveData = new MutableLiveData<>();

        Call<ArticleResponseWrapper> networkCall = sNewsApi.getHeadlines(
                specs.getCategory(),
                specs.getCountry()
        );

        networkCall.enqueue(new Callback<ArticleResponseWrapper>() {
            @Override
            public void onResponse(@NonNull Call<ArticleResponseWrapper> call, @NonNull retrofit2.Response<ArticleResponseWrapper> response) {
                if (response.raw().cacheResponse() != null) {
                    Timber.d("Response from cache");
                }

                if (response.raw().networkResponse() != null) {
                    Timber.d("Response from server");
                }
                if (response.body() != null) {
                    List<Article> articles = response.body().getArticles();
                    for (Article article : articles) {
                        article.setCategory(specs.getCategory());
                    }
                    networkArticleLiveData.setValue(articles);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArticleResponseWrapper> call, @NonNull Throwable t) {

            }
        });
        return networkArticleLiveData;
    }
}
