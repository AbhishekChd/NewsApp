package com.example.abhishek.newsapp.ui.news;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.abhishek.newsapp.R;
import com.example.abhishek.newsapp.adapters.NewsAdapter;
import com.example.abhishek.newsapp.databinding.NewsFragmentBinding;
import com.example.abhishek.newsapp.models.Article;
import com.example.abhishek.newsapp.models.Specification;
import com.example.abhishek.newsapp.network.NewsApi;

import java.util.List;

import timber.log.Timber;

public class NewsFragment extends Fragment implements NewsAdapter.NewsAdapterListener {
    public static final String PARAM_CATEGORY = "param-category";
    public static final String PARAM_LIST_STATE = "param-state";
    private final NewsAdapter newsAdapter = new NewsAdapter(null, this);
    private NewsApi.Category newsCategory;
    private NewsFragmentBinding binding;
    private boolean showSaved = false;
    private Parcelable listState;

    public static NewsFragment newInstance(NewsApi.Category category) {
        NewsFragment fragment = new NewsFragment();
        if (category == null) {
            return fragment;
        }
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
        } else {
            showSaved = true;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.news_fragment, container, false);
        RecyclerView recyclerView = binding.rvNewsPosts;
        recyclerView.setAdapter(newsAdapter);
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
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(PARAM_LIST_STATE);
        }
        NewsViewModel viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        if (showSaved) {
            viewModel.getAllSaved().observeForever(new Observer<List<Article>>() {
                @Override
                public void onChanged(@Nullable List<Article> articles) {
                    if (articles != null) {
                        newsAdapter.setArticles(articles);
                        restoreRecyclerViewState();
                    } else {
                        newsAdapter.notifyDataSetChanged();
                        restoreRecyclerViewState();
                    }
                }
            });
        } else {
            Specification specs = new Specification();
            specs.setCategory(newsCategory);
            viewModel.getNewsHeadlines(specs).observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
                @Override
                public void onChanged(@Nullable List<Article> articles) {
                    if (articles != null) {
                        newsAdapter.setArticles(articles);
                        restoreRecyclerViewState();
                    }
                }
            });
        }
    }

    @Override
    public void onNewsItemClicked(Article article) {
        Timber.d("Recieved article");
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.PARAM_ARTICLE, article);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
        binding.rvNewsPosts.setLayoutAnimation(controller);
        binding.rvNewsPosts.scheduleLayoutAnimation();
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().overridePendingTransition(R.anim.slide_up_animation, R.anim.fade_exit_transition);
        }
    }

    @Override
    public void onItemOptionsClicked(Article article) {
        OptionsBottomSheet bottomSheet = OptionsBottomSheet.getInstance(article.getTitle(), article.getUrl(), article.getId(), showSaved);
        if (getActivity() != null) {
            bottomSheet.show(getActivity().getSupportFragmentManager(), bottomSheet.getTag());
        } else {
            Timber.e("No Parent Activity was found!");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (binding.rvNewsPosts.getLayoutManager() != null) {
            listState = binding.rvNewsPosts.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(PARAM_LIST_STATE, listState);
        }
    }

    private void restoreRecyclerViewState() {
        if (binding.rvNewsPosts.getLayoutManager() != null) {
            binding.rvNewsPosts.getLayoutManager().onRestoreInstanceState(listState);
        }
    }
}
