package com.example.abhishek.newsapp.ui;

import android.appwidget.AppWidgetManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.ComponentName;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements OptionsBottomSheet.OptionsBottomSheetListener {
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private ActivityMainBinding binding;
    private HeadlinesFragment headlinesFragment;
    private SourceFragment sourceFragment;
    private NewsFragment newsFragment;
    private FirebaseAnalytics mFirebaseAnalytics;
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = new Bundle();
            switch (item.getItemId()) {
                case R.id.navigation_headlines:
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, headlinesFragment)
                            .commit();
                    bundle.putString(
                            FirebaseAnalytics.Param.ITEM_CATEGORY,
                            getString(R.string.title_headlines)
                    );
                    return true;
                case R.id.navigation_saved:
                    if (newsFragment == null) {
                        newsFragment = NewsFragment.newInstance(null);
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, newsFragment)
                            .commit();
                    bundle.putString(
                            FirebaseAnalytics.Param.ITEM_CATEGORY,
                            getString(R.string.title_saved)
                    );
                    return true;
                case R.id.navigation_sources:
                    if (sourceFragment == null) {
                        sourceFragment = SourceFragment.newInstance();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, sourceFragment)
                            .commit();
                    bundle.putString(
                            FirebaseAnalytics.Param.ITEM_CATEGORY,
                            getString(R.string.title_sources)
                    );
                    return true;
            }
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
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

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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
