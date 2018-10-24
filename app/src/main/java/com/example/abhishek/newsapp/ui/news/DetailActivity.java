package com.example.abhishek.newsapp.ui.news;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.abhishek.newsapp.R;
import com.example.abhishek.newsapp.databinding.ActivityDetailBinding;
import com.example.abhishek.newsapp.models.Article;

public class DetailActivity extends AppCompatActivity {
    public static final String PARAM_ARTICLE = "param-article";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(PARAM_ARTICLE)) {
            Article article = bundle.getParcelable(PARAM_ARTICLE);
            if (article != null) {
                binding.setArticle(article);
            }
        }
    }
}
