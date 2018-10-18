package com.example.abhishek.newsapp.old.utils;

import android.util.Log;

import com.example.abhishek.newsapp.old.models.NewsPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsJsonUtils {

    private static final String LOG_TAG = NewsJsonUtils.class.getSimpleName();

    private static final String NA_TOTAL_RESULTS = "totalResults";
    private static final String NA_ARTICLES = "articles";
    private static final String NA_SOURCE = "source";
    private static final String NA_SOURCE_NAME = "name";
    private static final String NA_TITLE = "title";
    private static final String NA_DESCRIPTION = "description";
    private static final String NA_URL_TO_IMAGE = "urlToImage";
    private static final String NA_URL_TO_POST = "url";

    public static List<NewsPost> getNewsFromJson(String newsJsonString) {
        List<NewsPost> newsPosts = new ArrayList<>();

        try {
            JSONObject newsJson = new JSONObject(newsJsonString);

            if (newsJson.getInt(NA_TOTAL_RESULTS) == 0) {
                return newsPosts;
            }

            JSONArray articles = newsJson.getJSONArray(NA_ARTICLES);
            JSONObject article;

            for (int i = 0, results = articles.length(); i < results; i++) {
                article = articles.getJSONObject(i);
                newsPosts.add(new NewsPost(
                                article.getString(NA_TITLE),
                                article.getString(NA_DESCRIPTION),
                                article.getJSONObject(NA_SOURCE).getString(NA_SOURCE_NAME),
                                article.getString(NA_URL_TO_IMAGE),
                                article.getString(NA_URL_TO_POST)
                        )
                );
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing JSON: " + e.getMessage());
        }

        return newsPosts;
    }
}
