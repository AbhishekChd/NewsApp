package com.example.abhishek.newsapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.abhishek.newsapp.data.NewsRepository;
import com.example.abhishek.newsapp.models.Article;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class SavedNewsService extends IntentService {

    public static final String ACTION_GET_NEXT = "com.example.abhishek.newsapp.widget.action.saved.next";
    public static final String ACTION_GET_PREVIOUS = "com.example.abhishek.newsapp.widget.action.saved.previous";
    private static final String ACTION_UPDATE_WIDGETS = "com.example.abhishek.newsapp.widget.action.update_widgets";

    public static final String PARAM_CURRENT = "com.example.abhishek.newsapp.widget.extra.current_selected";

    public SavedNewsService() {
        super("SavedNewsService");
    }

    public static void startActionNext(Context context, int currentIndex) {
        Intent intent = new Intent(context, SavedNewsService.class);
        intent.setAction(ACTION_GET_NEXT);
        intent.putExtra(PARAM_CURRENT, currentIndex);
        context.startService(intent);
    }

    public static void startActionPrevious(Context context, int currentIndex) {
        Intent intent = new Intent(context, SavedNewsService.class);
        intent.setAction(ACTION_GET_PREVIOUS);
        intent.putExtra(PARAM_CURRENT, currentIndex);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_NEXT.equals(action)) {
                final int param1 = intent.getIntExtra(PARAM_CURRENT, 0);
                handleActionNext(param1);
            } else if (ACTION_GET_PREVIOUS.equals(action)) {
                final int param1 = intent.getIntExtra(PARAM_CURRENT, 0);
                handleActionPrevious(param1);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionNext(final int currentIndex) {
        final LiveData<List<Article>> articlesLiveData = NewsRepository.getInstance(getApplicationContext()).getSaved();
        articlesLiveData.observeForever(new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null && articles.size() > currentIndex + 1) {
                    handleUpdateWidgets(articles, currentIndex + 1);
                    articlesLiveData.removeObserver(this);
                }
            }
        });
    }

    private void handleActionPrevious(final int currentIndex) {
        final LiveData<List<Article>> articlesLiveData = NewsRepository.getInstance(getApplicationContext()).getSaved();
        articlesLiveData.observeForever(new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null && articles.size() > 0 && currentIndex > 0) {
                    handleUpdateWidgets(articles, currentIndex - 1);
                    articlesLiveData.removeObserver(this);
                }
            }
        });
    }

    private void handleUpdateWidgets(List<Article> articles, int selected) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getApplicationContext(), SavedNewsWidget.class));
        SavedNewsWidget.updateNewsWidgets(getApplicationContext(), appWidgetManager, articles, selected, appWidgetIds);
    }
}
