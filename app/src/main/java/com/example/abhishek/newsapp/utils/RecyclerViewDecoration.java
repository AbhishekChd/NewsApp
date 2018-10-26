package com.example.abhishek.newsapp.utils;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Custom Item Decoration to set top and bottpm offset for showing the list
 */
public class RecyclerViewDecoration extends RecyclerView.ItemDecoration {
    private final int mHorizontalOffset;
    private final int mVerticalOffset;

    public RecyclerViewDecoration(int horizontalOffset, int verticalOffset) {
        mVerticalOffset = verticalOffset;
        mHorizontalOffset = horizontalOffset;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mHorizontalOffset, mVerticalOffset, mHorizontalOffset, mVerticalOffset);
    }

}
