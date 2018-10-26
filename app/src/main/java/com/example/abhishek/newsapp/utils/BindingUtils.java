package com.example.abhishek.newsapp.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.example.abhishek.newsapp.R;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class BindingUtils {
    private static final TimeZone timeZone = TimeZone.getTimeZone("UTC");

    /**
     * @param utcTimeString Time in UTC:+00 - Example: 2018-05-10T10:13:00Z
     * @return Formatted String of time elapsed by now in min/hrs/days
     */
    public static String getElapsedTime(long utcTimeString) {
        long timeElapsedInSeconds = (System.currentTimeMillis() - utcTimeString) / 1000;

        if (timeElapsedInSeconds < 60) {
            return "less than 1m";
        } else if (timeElapsedInSeconds < 3600) {
            timeElapsedInSeconds = timeElapsedInSeconds / 60;
            return timeElapsedInSeconds + "m";
        } else if (timeElapsedInSeconds < 86400) {
            timeElapsedInSeconds = timeElapsedInSeconds / 3600;
            return timeElapsedInSeconds + "h";
        } else {
            timeElapsedInSeconds = timeElapsedInSeconds / 86400;
            return timeElapsedInSeconds + "d";
        }
    }

    /**
     * Utility method for fetching formatted News Source and Time
     *
     * @param sourceName Article source name
     * @param date       Publish date of article
     * @return Formatted outputted Example: <b>CNN • 7h</b>
     */
    public static String getSourceAndTime(String sourceName, Timestamp date) {
        return sourceName + " • " + getElapsedTime(date.getTime());
    }

    /**
     * Utility method for Image url If image url is valid then it is parsed else
     * Article url provides url to website and icon finder utility is used to find icon
     *
     * @param imageView  Default view passed for displaying image
     * @param url        Url of the image
     * @param articleUrl URL to the article
     */
    @BindingAdapter({"bind:url", "bind:articleUrl"})
    public static void loadThumbnailImage(ImageView imageView, String url, String articleUrl) {
        Context context = imageView.getContext();
        if (url == null) {
            String iconUrl = "https://besticon-demo.herokuapp.com/icon?url=%s&size=80..120..200";
            url = String.format(iconUrl, Uri.parse(articleUrl).getAuthority());
        }
        GlideApp.with(imageView)
                .load(url)
                .apply(NewsGlideModule.roundedCornerImage(new RequestOptions(), imageView.getContext(), 4))
                .placeholder(context.getResources().getDrawable(R.color.cardBackground))
                .into(imageView);
    }

    /**
     * Utility method for Image url If image url is valid then it is parsed else
     * Article url provides url to website and icon finder utility is used to find icon
     * This puts a radius 0 to image
     *
     * @param imageView  Default view passed for displaying image
     * @param url        Url of the image
     * @param articleUrl URL to the article
     */
    @BindingAdapter({"bind:urlToImage", "bind:articleUrl"})
    public static void loadImage(ImageView imageView, String url, String articleUrl) {
        Context context = imageView.getContext();
        if (url == null) {
            String iconUrl = "https://besticon-demo.herokuapp.com/icon?url=%s&size=80..120..200";
            url = String.format(iconUrl, Uri.parse(articleUrl).getAuthority());
        }
        GlideApp.with(imageView)
                .load(url)
                .apply(NewsGlideModule.roundedCornerImage(new RequestOptions(), imageView.getContext(), 0))
                .placeholder(context.getResources().getDrawable(R.color.cardBackground))
                .into(imageView);
    }

    /**
     * Truncate extra characters at the end of each content
     * Remove string at end similar to [18040+ chars]
     *
     * @param content Unformatted content
     * @return Formatted contented
     */
    public static String truncateExtra(String content) {
        if (content == null)
            return "";
        return content.replaceAll("(\\[\\+\\d+ chars])", "");
    }

    /**
     * Format date and time for details activity
     *
     * @param date Timestamp for current date
     * @return Formatted date of format <b>01 Oct 2018 | 02:45PM</b>
     */
    public static String formatDateForDetails(Timestamp date) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy | hh:mm aaa", Locale.getDefault());
        return format.format(new Date(date.getTime()));
    }

    @BindingAdapter("bind:sourceUrl")
    public static void loadSourceImage(ImageView imageView, String sourceUrl) {
        Context context = imageView.getContext();
        String iconUrl = "https://besticon-demo.herokuapp.com/icon?url=%s&size=80..120..200";
        sourceUrl = String.format(iconUrl, Uri.parse(sourceUrl).getAuthority());

        GlideApp.with(imageView)
                .load(sourceUrl)
                .apply(NewsGlideModule.roundedCornerImage(new RequestOptions(), context, 4))
                .placeholder(context.getResources().getDrawable(R.color.cardBackground))
                .into(imageView);
    }
}


