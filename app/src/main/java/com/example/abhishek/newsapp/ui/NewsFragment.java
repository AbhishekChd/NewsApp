package com.example.abhishek.newsapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhishek.newsapp.R;
import com.example.abhishek.newsapp.adapters.NewsAdapter;
import com.example.abhishek.newsapp.databinding.NewsFragmentBinding;
import com.example.abhishek.newsapp.models.Article;
import com.example.abhishek.newsapp.models.Specification;
import com.example.abhishek.newsapp.network.NewsApi;
import com.example.abhishek.newsapp.utils.RecyclerViewDecoration;

import java.util.List;

import timber.log.Timber;

public class NewsFragment extends Fragment implements NewsAdapter.NewsAdapterListener {
    public static final String PARAM_CATEGORY = "param-category";
    private final NewsAdapter newsAdapter = new NewsAdapter(null, this);
    private NewsApi.Category newsCategory;
    private NewsFragmentBinding binding;

    public static NewsFragment newInstance(NewsApi.Category category) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_CATEGORY, category.name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newsCategory = NewsApi.Category
                    .valueOf(getArguments().getString(PARAM_CATEGORY));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.news_fragment, container, false);
        RecyclerView recyclerView = binding.rvNewsPosts;
        recyclerView.setAdapter(newsAdapter);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(
                Math.round(getResources().getDimension(R.dimen.recycler_view_offset))
        ));
        if (getContext() != null) {
            DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            divider.setDrawable(getResources().getDrawable(R.drawable.recycler_view_divider));
            recyclerView.addItemDecoration(divider);
        }

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NewsViewModel viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        Specification specs = new Specification();
        specs.setCategory(newsCategory);
        viewModel.getNewsHeadlines(specs).observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    newsAdapter.setArticles(articles);
                    Timber.d("%s", articles.toString());
                }
            }
        });
    }

    @Override
    public void onNewsItemClicked(Article article) {

    }
}
