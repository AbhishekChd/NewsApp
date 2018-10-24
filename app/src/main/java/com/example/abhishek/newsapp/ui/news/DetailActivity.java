package com.example.abhishek.newsapp.ui.news;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.abhishek.newsapp.R;
import com.example.abhishek.newsapp.databinding.ActivityDetailBinding;
import com.example.abhishek.newsapp.models.Article;

public class DetailActivity extends AppCompatActivity {
    public static final String PARAM_ARTICLE = "param-article";
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        setupArticleAndListener();
    }

    /**
     * Extracts Article from Arguments and Adds button listeners
     */
    private void setupArticleAndListener() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(PARAM_ARTICLE)) {
            final Article article = bundle.getParcelable(PARAM_ARTICLE);
            if (article != null) {
                binding.setArticle(article);
                binding.ivShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        String shareText = article.getTitle() + "\n" + article.getUrl();
                        intent.putExtra(Intent.EXTRA_TEXT, shareText);
                        intent.setType("text/plain");

                        startActivity(intent);
                    }
                });
                binding.btnReadFull.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}