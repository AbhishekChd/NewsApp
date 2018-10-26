package com.example.abhishek.newsapp.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhishek.newsapp.R;
import com.example.abhishek.newsapp.databinding.SourceItemBinding;
import com.example.abhishek.newsapp.models.Source;

import java.util.List;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.SourceViewHolder> {
    private final SourceAdapterListener listener;
    private List<Source> sources;
    private LayoutInflater layoutInflater;

    public SourceAdapter(List<Source> sources, SourceAdapterListener listener) {
        this.sources = sources;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        SourceItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.source_item, parent, false);
        return new SourceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SourceViewHolder sourceViewHolder, int i) {
        sourceViewHolder.binding.setSource(sources.get(i));
        sourceViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return sources == null ? 0 : sources.size();
    }

    public void setSources(List<Source> sources) {
        if (sources != null) {
            this.sources = sources;
            notifyDataSetChanged();
        }
    }

    public interface SourceAdapterListener {
        void onSourceItemClicked(Source source);
    }

    class SourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final SourceItemBinding binding;

        SourceViewHolder(final SourceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = this.getAdapterPosition();
            listener.onSourceItemClicked(sources.get(index));

        }
    }
}
