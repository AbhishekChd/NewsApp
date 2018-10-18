package com.example.abhishek.newsapp.old.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abhishek.newsapp.R;
import com.example.abhishek.newsapp.old.models.NewsArticle;
import com.example.abhishek.newsapp.old.utils.DateTimeUtils;
import com.squareup.picasso.Picasso;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsPostViewHolder> {

    private Context context;
    private List<NewsArticle> newsPosts = null;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void setNewsPosts(List<NewsArticle> newsPosts) {
        this.newsPosts = newsPosts;
        notifyDataSetChanged();
    }

    @Override
    public NewsPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);
        return new NewsPostViewHolder(view);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onBindViewHolder(NewsPostViewHolder holder, int position) {
        NewsArticle article = newsPosts.get(position);
        holder.title.setText(article.getTitle());
        holder.source.setText(context.getString(
                R.string.headline_date_format,
                article.getSource().getName(),
                DateTimeUtils.getElapsedTime(article.getPublishedAt())
        ));
        String urlToImage = article.getUrlToImage();


        if (urlToImage != null && !TextUtils.isEmpty(urlToImage) && !urlToImage.equals("")) {
            Picasso.get()
                    .load(urlToImage)
                    .fit()
                    .centerCrop()
                    .error(R.mipmap.ic_news_logo)
                    .into(holder.imageView);
            Log.v(getClass().getSimpleName(), urlToImage);
        } else {
            Picasso.get()
                    .load(R.mipmap.ic_news_logo)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (newsPosts != null && !newsPosts.isEmpty()) {
            return newsPosts.size();
        }
        return 0;
    }

    class NewsPostViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView source;
        private ImageView imageView;


        public NewsPostViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_news_title);
            source = itemView.findViewById(R.id.tv_news_source);
            imageView = itemView.findViewById(R.id.iv_news_image);
        }
    }
}
