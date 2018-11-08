package com.example.abhishek.newsapp.ui;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.abhishek.newsapp.BuildConfig;
import com.example.abhishek.newsapp.R;
import com.example.abhishek.newsapp.data.NewsRepository;
import com.example.abhishek.newsapp.databinding.ActivityMainBinding;
import com.example.abhishek.newsapp.models.Article;
import com.example.abhishek.newsapp.ui.headlines.HeadlinesFragment;
import com.example.abhishek.newsapp.ui.news.NewsFragment;
import com.example.abhishek.newsapp.ui.news.OptionsBottomSheet;
import com.example.abhishek.newsapp.ui.sources.SourceFragment;
import com.example.abhishek.newsapp.widget.SavedNewsWidget;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements OptionsBottomSheet.OptionsBottomSheetListener {
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private ActivityMainBinding binding;
    private HeadlinesFragment headlinesFragment;
    private SourceFragment sourceFragment;
    private NewsFragment newsFragment;
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_headlines:
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, headlinesFragment)
                            .commit();
                    return true;
                case R.id.navigation_saved:
                    if (newsFragment == null) {
                        newsFragment = NewsFragment.newInstance(null);
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, newsFragment)
                            .commit();
                    return true;
                case R.id.navigation_sources:
                    if (sourceFragment == null) {
                        sourceFragment = SourceFragment.newInstance();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, sourceFragment)
                            .commit();
                    return true;
            }
            return false;
        }
    };
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        // Bind data using DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            // Add a default fragment
            headlinesFragment = HeadlinesFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, headlinesFragment)
                    .commit();
        }

        setupToolbar();

        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        final LiveData<List<Article>> saved = NewsRepository.getInstance(this).getSaved();
        saved.observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getApplicationContext(), SavedNewsWidget.class));
                    if (articles.size() == 0) {
                        SavedNewsWidget.updateNewsWidgets(getApplicationContext(), appWidgetManager, articles, -1, appWidgetIds);
                    } else {
                        SavedNewsWidget.updateNewsWidgets(getApplicationContext(), appWidgetManager, articles, 0, appWidgetIds);
                    }
                }
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));
            //Remove trailing space from toolbar
            binding.toolbar.setContentInsetsAbsolute(10, 10);
        }
    }

    @Override
    public void onSaveToggle(String text) {
        if (snackbar == null) {
            snackbar = Snackbar.make(binding.coordinator, "Hello", Snackbar.LENGTH_SHORT);
            final CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbar.getView().getLayoutParams();
            params.setMargins(
                    (int) getResources().getDimension(R.dimen.snackbar_margin_vertical),
                    0,
                    (int) getResources().getDimension(R.dimen.snackbar_margin_vertical),
                    (int) getResources().getDimension(R.dimen.snackbar_margin_horizontal)
            );
            snackbar.getView().setLayoutParams(params);
            snackbar.getView().setPadding(
                    (int) getResources().getDimension(R.dimen.snackbar_padding),
                    (int) getResources().getDimension(R.dimen.snackbar_padding),
                    (int) getResources().getDimension(R.dimen.snackbar_padding),
                    (int) getResources().getDimension(R.dimen.snackbar_padding)
            );
        }
        if (snackbar.isShown()) {
            snackbar.dismiss();
        }
        snackbar.setText(text);
        snackbar.show();
    }
}
