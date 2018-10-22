package com.example.abhishek.newsapp.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.example.abhishek.newsapp.R;

import java.sql.Timestamp;

import timber.log.Timber;

public class BindingUtils {
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
        Timber.d("Date : %s", date.toString());
        Timber.d("Date : %s", date.getTime());
        StringBuilder builder = new StringBuilder();
        builder.append(sourceName)
                .append(" • ")
                .append(getElapsedTime(date.getTime()));
        return builder.toString();
    }

    /**
     * Utility method for Image url
     *
     * @param imageView Default view passed for displaying image
     * @param url       Url of the image
     */
    @BindingAdapter({"newsImage"})
    public static void loadImage(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Timber.d("Image Url : %s", url);
        GlideApp.with(imageView)
                .load(url)
                .apply(NewsGlideModule.roundedCornerImage(new RequestOptions(), imageView.getContext(), 4))
                .placeholder(context.getResources().getDrawable(R.color.cardBackground))
                .into(imageView);
    }
}


