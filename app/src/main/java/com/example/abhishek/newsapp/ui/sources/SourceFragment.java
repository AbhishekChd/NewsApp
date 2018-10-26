package com.example.abhishek.newsapp.ui.sources;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhishek.newsapp.R;
import com.example.abhishek.newsapp.adapters.SourceAdapter;
import com.example.abhishek.newsapp.databinding.FragmentSourceBinding;
import com.example.abhishek.newsapp.models.Source;
import com.example.abhishek.newsapp.models.Specification;
import com.example.abhishek.newsapp.utils.RecyclerViewDecoration;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SourceFragment extends Fragment implements SourceAdapter.SourceAdapterListener {

    private final SourceAdapter sourceAdapter = new SourceAdapter(null, this);
    private FragmentSourceBinding binding;

    public SourceFragment() {
        // Required empty public constructor
    }

    public static SourceFragment newInstance() {
        return new SourceFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_source, container, false);

        setupViewModel();
        binding.rvSources.setAdapter(sourceAdapter);
        binding.rvSources.addItemDecoration(new RecyclerViewDecoration(
                Math.round(getResources().getDimension(R.dimen.rv_horizontal_offset)),
                Math.round(getResources().getDimension(R.dimen.rv_vertical_offset))
        ));
        if (getContext() != null) {
            DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            divider.setDrawable(getResources().getDrawable(R.drawable.recycler_view_divider));
            binding.rvSources.addItemDecoration(divider);
        }

        return binding.getRoot();
    }

    private void setupViewModel() {
        SourceViewModel viewModel = ViewModelProviders.of(this).get(SourceViewModel.class);
        Specification specification = new Specification();
        specification.setLanguage(Locale.getDefault().getLanguage());
        specification.setCountry(null);
        viewModel.getSource(specification).observe(this, new Observer<List<Source>>() {
            @Override
            public void onChanged(@Nullable List<Source> sources) {
                if (sources != null) {
                    sourceAdapter.setSources(sources);
                }
            }
        });
    }

    @Override
    public void onSourceItemClicked(Source source) {

    }
}
