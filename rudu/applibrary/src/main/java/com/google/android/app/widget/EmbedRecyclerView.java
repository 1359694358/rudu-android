package com.google.android.app.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


public class EmbedRecyclerView extends RecyclerView {
    public EmbedRecyclerView(Context context) {
        super(context);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
    }

    public EmbedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
    }

    public EmbedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);

        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
        requestLayout();
        setNestedScrollingEnabled(false);

    }
}
