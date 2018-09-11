package com.uautogo.qidian.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by serenade on 17-11-11.
 */

public class LinearDivider extends RecyclerView.ItemDecoration {
    private int mWidth;
    private int mColor;
    private Paint mPaint;
    private final Rect mBounds = new Rect();

    public LinearDivider() {
        this(10, Color.LTGRAY);
    }

    public LinearDivider(int mWidth, int mColor) {
        this.mWidth = mWidth;
        this.mColor = mColor;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null)
            return;

        if (((LinearLayoutManager) parent.getLayoutManager()).getOrientation() == OrientationHelper.HORIZONTAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (DividerHelper.isLastRow(parent.getChildAdapterPosition(child)
                    , 1
                    , parent.getAdapter().getItemCount()
                    , ((LinearLayoutManager) parent.getLayoutManager()).getOrientation()))
                continue;
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            int top = bottom - mWidth;
            int left = child.getLeft();
            int right = child.getRight();
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (DividerHelper.isLastColumn(parent.getChildAdapterPosition(child)
                    , 1
                    , parent.getAdapter().getItemCount()
                    , ((LinearLayoutManager) parent.getLayoutManager()).getOrientation()))
                continue;
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - mWidth;
            int top = child.getTop();
            int bottom = child.getBottom();
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null)
            return;
        int bottom = mWidth;
        int right = mWidth;
        if (DividerHelper.isLastRow(parent.getChildAdapterPosition(view)
                , 1
                , parent.getAdapter().getItemCount()
                , ((LinearLayoutManager) parent.getLayoutManager()).getOrientation()))
            bottom = 0;
        if (DividerHelper.isLastColumn(parent.getChildAdapterPosition(view)
                , 1
                , parent.getAdapter().getItemCount()
                , ((LinearLayoutManager) parent.getLayoutManager()).getOrientation()))
            right = 0;
        outRect.set(0, 0, right, bottom);
    }
}
