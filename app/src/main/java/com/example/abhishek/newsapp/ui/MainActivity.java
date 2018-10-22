package com.example.abhishek.newsapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.abhishek.newsapp.BuildConfig;
import com.example.abhishek.newsapp.R;
import com.example.abhishek.newsapp.databinding.ActivityMainBinding;
import com.example.abhishek.newsapp.ui.headlines.HeadlinesFragment;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private HeadlinesFragment headlinesFragment;
    private final FragmentManager fragmentManager = getSupportFragmentManager();

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

                    return true;
                case R.id.navigation_sources:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        // Bind data using DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Add a default fragment
        headlinesFragment = HeadlinesFragment.newInstance();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, headlinesFragment)
                .commit();

        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));
        }
    }
}
