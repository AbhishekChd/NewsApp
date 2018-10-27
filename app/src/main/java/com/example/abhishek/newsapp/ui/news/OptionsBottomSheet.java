package com.example.abhishek.newsapp.ui.news;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhishek.newsapp.R;
import com.example.abhishek.newsapp.data.NewsRepository;
import com.example.abhishek.newsapp.databinding.FragmentOptionsBottomSheetBinding;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    private static final String PARAM_TITLE = "param-title";
    private static final String PARAM_URL = "param-url";
    private static final String PARAM_ID = "param-id";
    private static final String PARAM_SAVED = "param-saved";
    private static OptionsBottomSheet fragment;
    private String title;
    private String url;
    private int id;
    private boolean isSaved;

    public OptionsBottomSheet() {
        // Required empty public constructor
    }

    public static OptionsBottomSheet getInstance(String title, String url, int id, boolean isSaved) {
        fragment = new OptionsBottomSheet();
        Bundle args = new Bundle();
        args.putString(PARAM_TITLE, title);
        args.putString(PARAM_URL, url);
        args.putInt(PARAM_ID, id);
        args.putBoolean(PARAM_SAVED, isSaved);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(PARAM_TITLE);
            url = getArguments().getString(PARAM_URL);
            id = getArguments().getInt(PARAM_ID);
            isSaved = getArguments().getBoolean(PARAM_SAVED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentOptionsBottomSheetBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_options_bottom_sheet, container, false);

        if (isSaved) {
            binding.btnSave.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_saved_item, 0, 0, 0);
        }
        binding.btnShare.setOnClickListener(this);
        binding.btnOpenInBrowser.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_open_in_browser:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                fragment.dismiss();
                startActivity(intent);
                break;
            case R.id.btn_share:
                String shareText = title + "\n" + url;
                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, shareText);
                intent.setType("text/plain");
                this.dismiss();
                startActivity(intent);
                break;
            case R.id.btn_save:
                if (isSaved) {
                    NewsRepository.getInstance(getContext()).removeSaved(id);
                } else {
                    NewsRepository.getInstance(getContext()).save(id);
                }
                Timber.d("Saved for id  : %s", id);
                dismiss();
                break;
        }
    }
}
