package com.example.abhishek.newsapp.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.DisplayMetrics;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

@GlideModule
public final class NewsGlideModule extends AppGlideModule {

    @NonNull
    @GlideOption
    public static RequestOptions roundedCornerImage(RequestOptions options, @NonNull Context context, int radius) {
        if (radius > 0) {
            int px = Math.round(radius * (context.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
            return options.transforms(new CenterCrop(), new RoundedCorners(px));
        }
        return options.transforms(new CenterCrop());
    }
}
