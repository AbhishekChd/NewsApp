package com.example.abhishek.newsapp.network;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class NewsRequestQueue extends RequestQueue {
    private static NewsRequestQueue mInstance;
    private static Context mContext;
    private RequestQueue mRequestQueue;

    private NewsRequestQueue(Context context, Cache cache, Network network) {
        super(cache, network);

        mContext = context;
    }

    public static synchronized NewsRequestQueue getInstance(Context context) {
        if (mContext == null) {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);// 1MB
            Network network = new BasicNetwork(new HurlStack());
            mInstance = new NewsRequestQueue(context, cache, network);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
