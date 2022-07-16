package com.example.abhishek.newsapp.ui.headlines;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhishek.newsapp.R;
import com.example.abhishek.newsapp.databinding.FragmentHeadlinesBinding;
import com.example.abhishek.newsapp.network.NewsApi;
import com.google.android.material.tabs.TabLayout;

public class HeadlinesFragment extends Fragment {
    private final String[] categories = {
            NewsApi.Category.general.name(),
            NewsApi.Category.business.name(),
            NewsApi.Category.sports.name(),
            NewsApi.Category.health.name(),
            NewsApi.Category.entertainment.name(),
            NewsApi.Category.technology.name(),
            NewsApi.Category.science.name(),
    };
    private final int[] categoryIcons = {
            R.drawable.ic_headlines,
            R.drawable.nav_business,
            R.drawable.nav_sports,
            R.drawable.nav_health,
            R.drawable.nav_entertainment,
            R.drawable.nav_tech,
            R.drawable.nav_science,

    };
    private FragmentHeadlinesBinding binding;

    public HeadlinesFragment() {
        // Required empty public constructor
    }

    public static HeadlinesFragment newInstance() {
        return new HeadlinesFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_headlines, container, false);

        ViewCompat.setElevation(binding.tablayoutHeadlines, getResources().getDimension(R.dimen.tab_layout_elevation));

        if (getActivity() != null) {
            ViewPagerAdapter viewPager = new ViewPagerAdapter(getChildFragmentManager(), categories);
            binding.viewpagerHeadlines.setAdapter(viewPager);
            binding.tablayoutHeadlines.setupWithViewPager(binding.viewpagerHeadlines);
            setupTabIcons();
        }
        return this.binding.getRoot();
    }

    private void setupTabIcons() {
        TabLayout.Tab tab;
        for (int i = 0; i < categories.length; i++) {
            tab = binding.tablayoutHeadlines.getTabAt(i);
            if (tab != null) {
                tab.setIcon(categoryIcons[i]).setText(categories[i]);
            }
        }
    }
}
