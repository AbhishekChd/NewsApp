package com.example.abhishek.newsapp.utils;

import android.net.Uri;
import android.util.Log;

import com.example.abhishek.newsapp.contracts.BaseUrlContract;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();


    /**
     * Builds the URL to query NewsAPI
     *
     * @return The URL to query the NewsAPI
     */
    public static URL buildURL(String builtUri) {
        URL url = null;
        try {
            url = new URL(builtUri);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed URL: " + e.getMessage());
        }

        assert url != null;
        Log.v(LOG_TAG, "Built URL: " + url.toString());

        return url;
    }

    public static URL getHeadlines(int category) {
        Uri.Builder builtUri = Uri.parse(BaseUrlContract.NEWS_URL).buildUpon();
        builtUri.appendPath(BaseUrlContract.TOP_HEADLINES_URL)
                .appendQueryParameter(BaseUrlContract.PARAM_COUNTRY, "in");
        switch (category) {
            case BaseUrlContract.HEADLINES_BUSINESS:
                builtUri.appendQueryParameter(BaseUrlContract.PARAM_CATEGORY, BaseUrlContract.PARAM_BUSINESS);
                break;
            case BaseUrlContract.HEADLINES_ENTERTAINMENT:
                builtUri.appendQueryParameter(BaseUrlContract.PARAM_CATEGORY, BaseUrlContract.PARAM_ENTERTAINMENET);
                break;
            case BaseUrlContract.HEADLINES_HEALTH:
                builtUri.appendQueryParameter(BaseUrlContract.PARAM_CATEGORY, BaseUrlContract.PARAM_HEALTH);
                break;
            case BaseUrlContract.HEADLINES_SCIENCE:
                builtUri.appendQueryParameter(BaseUrlContract.PARAM_CATEGORY, BaseUrlContract.PARAM_SCIENCE);
                break;
            case BaseUrlContract.HEADLINES_SPORTS:
                builtUri.appendQueryParameter(BaseUrlContract.PARAM_CATEGORY, BaseUrlContract.PARAM_SPORTS);
                break;
            case BaseUrlContract.HEADLINES_TECHNOLOGY:
                builtUri.appendQueryParameter(BaseUrlContract.PARAM_CATEGORY, BaseUrlContract.PARAM_TECHNOLOGY);
                break;
        }
        return buildURL(builtUri.toString());
    }

    /**
     * This method returns entire result form HTTP response
     *
     * @param url The URL to fetch the HTTP response from
     * @return The contents of HTTP response
     */
    public static String getResponseFromHttpUrl(URL url) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                return scanner.next();
            } else return null;

        } catch (IOException e) {
            Log.e(LOG_TAG, "Couldn't Open URL connection: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}
