package com.uautogo.qidian.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Serenade on 2017/8/1.
 */

public class DividerDecoration extends RecyclerView.ItemDecoration {
    private boolean isXRecyclerView = false;

    public DividerDecoration(boolean isXRecyclerView) {
        this.isXRecyclerView = isXRecyclerView;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (manager.findLastVisibleItemPosition() != adapter.getItemCount()) {
            // recyclerView是否设置了paddingLeft和paddingRight
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int childCount = parent.getChildCount();

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.GRAY);
            int i = 0;
            if (isXRecyclerView)
                i = 1;
            for (; i < childCount - 1; i++) {
                View child = parent.getChildAt(i);
                int position = parent.getChildAdapterPosition(child);
                if (position == adapter.getItemCount())
                    break;
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                // divider的top 应该是 item的bottom 加上 marginBottom 再加上 Y方向上的位移
                int top = child.getBottom() + params.bottomMargin +
                        Math.round(ViewCompat.getTranslationY(child));
                // divider的bottom就是top加上divider的高度了
                int bottom = (int) (top + 1);
                c.drawRect(left, top, right, bottom, paint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        LinearLayoutManager manager = (LinearLayoutManager) parent.getLayoutManager();
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (manager.findLastVisibleItemPosition() != adapter.getItemCount())
            outRect.bottom = 1;
    }
}
