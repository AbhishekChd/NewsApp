package com.example.abhishek.newsapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.abhishek.newsapp.data.dao.HeadlinesDao;
import com.example.abhishek.newsapp.data.dao.SavedDao;
import com.example.abhishek.newsapp.data.dao.SourcesDao;
import com.example.abhishek.newsapp.models.Article;
import com.example.abhishek.newsapp.models.SavedArticle;
import com.example.abhishek.newsapp.models.Source;
import com.example.abhishek.newsapp.models.Specification;
import com.example.abhishek.newsapp.network.NewsApiClient;
import com.example.abhishek.newsapp.utils.AppExecutors;

import java.util.List;

import timber.log.Timber;

public class NewsRepository {
    private static final Object LOCK = new Object();
    private static NewsRepository sInstance;

    private final NewsApiClient newsApiService;
    private final HeadlinesDao headlinesDao;
    private final SourcesDao sourcesDao;
    private final SavedDao savedDao;
    private final AppExecutors mExecutor;
    private final MutableLiveData<List<Article>> networkArticleLiveData;
    private final MutableLiveData<List<Source>> networkSourceLiveData;

    // required private constructor for Singleton pattern
    private NewsRepository(Context context) {
        newsApiService = NewsApiClient.getInstance(context);
        headlinesDao = NewsDatabase.getInstance(context).headlinesDao();
        sourcesDao = NewsDatabase.getInstance(context).sourcesDao();
        savedDao = NewsDatabase.getInstance(context).savedDao();
        mExecutor = AppExecutors.getInstance();
        networkArticleLiveData = new MutableLiveData<>();
        networkSourceLiveData = new MutableLiveData<>();
        networkArticleLiveData.observeForever(new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable final List<Article> articles) {
                if (articles != null) {
                    mExecutor.getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            headlinesDao.bulkInsert(articles);
                        }
                    });
                }
            }
        });
        networkSourceLiveData.observeForever(new Observer<List<Source>>() {
            @Override
            public void onChanged(@Nullable final List<Source> sources) {
                if (sources != null) {
                    mExecutor.getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            sourcesDao.bulkInsert(sources);
                        }
                    });
                }
            }
        });
    }

    public synchronized static NewsRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NewsRepository(context);
            }
        }
        return sInstance;
    }

    public LiveData<List<Article>> getHeadlines(final Specification specs) {
        final LiveData<List<Article>> networkData = newsApiService.getHeadlines(specs);
        networkData.observeForever(new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    networkArticleLiveData.setValue(articles);
                    networkData.removeObserver(this);
                }
            }
        });
        return headlinesDao.getArticleByCategory(specs.getCategory());
    }

    public LiveData<List<Source>> getSources(final Specification specs) {
        final LiveData<List<Source>> networkData = newsApiService.getSources(specs);
        networkData.observeForever(new Observer<List<Source>>() {
            @Override
            public void onChanged(@Nullable List<Source> sources) {
                if (sources != null) {
                    networkSourceLiveData.setValue(sources);
                    networkData.removeObserver(this);
                }
            }
        });
        return sourcesDao.getAllSources();
    }

    public LiveData<List<Article>> getSaved() {
        return savedDao.getAllSaved();
    }

    public LiveData<Boolean> isSaved(int articleId) {
        return savedDao.isFavourite(articleId);
    }

    public void removeSaved(final int articleId) {
        mExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                savedDao.removeSaved(articleId);
            }
        });
    }

    public void save(final int articleId) {
        mExecutor.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                SavedArticle savedArticle = new SavedArticle(articleId);
                savedDao.insert(savedArticle);
                Timber.d("Saved in database for id  : %s", articleId);
            }
        });
    }
}