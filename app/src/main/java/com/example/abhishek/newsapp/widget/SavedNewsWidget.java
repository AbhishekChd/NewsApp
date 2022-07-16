package com.example.abhishek.newsapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RemoteViews;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.abhishek.newsapp.R;
import com.example.abhishek.newsapp.models.Article;
import com.example.abhishek.newsapp.ui.MainActivity;
import com.example.abhishek.newsapp.ui.news.DetailActivity;
import com.example.abhishek.newsapp.utils.GlideApp;

import java.util.List;

import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class SavedNewsWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, List<Article> articles, int selected,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.saved_news_widget);
        if (articles != null && selected > -1) {
            views.setViewVisibility(R.id.iv_news_image, View.VISIBLE);
            views.setViewVisibility(R.id.tv_news_title, View.VISIBLE);
            views.setViewVisibility(R.id.tv_page, View.VISIBLE);
            views.setViewVisibility(R.id.ib_next, View.VISIBLE);
            views.setViewVisibility(R.id.ib_previous, View.VISIBLE);
            views.setViewVisibility(R.id.tv_error, View.GONE);
            Timber.d("Articles %d", articles.size());
            if (articles.size() > 0 && selected < articles.size()) {
                views.setTextViewText(R.id.tv_news_title, articles.get(selected).getTitle());

                AppWidgetTarget appWidgetTarget = new AppWidgetTarget(context, R.id.iv_news_image, views, appWidgetId) {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        super.onResourceReady(resource, transition);
                    }
                };
                GlideApp
                        .with(context.getApplicationContext())
                        .asBitmap()
                        .load(articles.get(selected).getUrlToImage())
                        .into(appWidgetTarget);
                views.setTextViewText(R.id.tv_page, (selected + 1) + "/" + articles.size());
            }

            Intent nextIntent = new Intent(context, SavedNewsService.class);
            nextIntent.setAction(SavedNewsService.ACTION_GET_NEXT);
            nextIntent.putExtra(SavedNewsService.PARAM_CURRENT, selected);
            PendingIntent nextPendingIntent = PendingIntent.getService(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.ib_next, nextPendingIntent);

            Intent previousIntent = new Intent(context, SavedNewsService.class);
            previousIntent.setAction(SavedNewsService.ACTION_GET_PREVIOUS);
            previousIntent.putExtra(SavedNewsService.PARAM_CURRENT, selected);
            PendingIntent previousPendingIntent = PendingIntent.getService(context, 0, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.ib_previous, previousPendingIntent);

            Intent detail = new Intent(context, DetailActivity.class);
            detail.putExtra(DetailActivity.PARAM_ARTICLE, articles.get(selected));
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, detail, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_parent, pendingIntent);

        } else {
            views.setViewVisibility(R.id.iv_news_image, View.GONE);
            views.setViewVisibility(R.id.tv_news_title, View.GONE);
            views.setViewVisibility(R.id.tv_page, View.GONE);
            views.setViewVisibility(R.id.ib_next, View.GONE);
            views.setViewVisibility(R.id.ib_previous, View.GONE);
            views.setViewVisibility(R.id.tv_error, View.VISIBLE);

            Intent home = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, home, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_parent, pendingIntent);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent nextIntent = new Intent(context, SavedNewsService.class);
        nextIntent.setAction(SavedNewsService.ACTION_GET_NEXT);
        nextIntent.putExtra(SavedNewsService.PARAM_CURRENT, -1);
        PendingIntent nextPendingIntent = PendingIntent.getService(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            nextPendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }

    }

    public static void updateNewsWidgets(Context context, AppWidgetManager appWidgetManager, List<Article> articles, int selected, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, articles, selected, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

