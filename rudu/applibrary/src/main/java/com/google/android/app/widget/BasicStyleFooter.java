package com.google.android.app.widget;
import android.content.Context;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.app.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

public class BasicStyleFooter extends RelativeLayout implements RefreshFooter
{
    Context mContext;
    private View mProgressBar;
    private TextView mHintView;
    private final int ROTATE_ANIM_DURATION = 180;
    public BasicStyleFooter(Context context)
    {
        super(context);
        init(context);
    }

    protected void init(Context context)
    {
        mContext = context;
        inflate(mContext, R.layout.classic_footer,this);
        mProgressBar = findViewById(R.id.footer_progressbar);
        mHintView =  findViewById(R.id.footer_hint_textview);
    }

    boolean mNoMoreData=false;
    @Override
    public boolean setNoMoreData(boolean noMoreData)
    {
        if (mNoMoreData != noMoreData) {
            mProgressBar.setVisibility(INVISIBLE);
            mHintView.setVisibility(VISIBLE);
            if(noMoreData)
            {
                mHintView.setText(R.string.all_finished_load);
            }
            else
            {
                mHintView.setText(R.string.xlistview_footer_hint_normal1);
            }
            mNoMoreData = noMoreData;
        }
        return true;
    }

    @NonNull
    @Override
    public View getView()
    {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle()
    {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors)
    {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight)
    {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight)
    {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight)
    {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight)
    {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success)
    {
        return ROTATE_ANIM_DURATION;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax)
    {

    }

    @Override
    public boolean isSupportHorizontalDrag()
    {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState)
    {
        switch (newState)
        {
            case PullUpCanceled:
                mProgressBar.setVisibility(View.INVISIBLE);
                mHintView.setVisibility(VISIBLE);
                mHintView.setText(R.string.xlistview_footer_hint_normal1);
                break;
            case LoadReleased:
            case Loading:
                mProgressBar.setVisibility(VISIBLE);
                mHintView.setVisibility(INVISIBLE);
                break;
            case ReleaseToLoad:
            case PullUpToLoad:
                mProgressBar.setVisibility(View.INVISIBLE);
                mHintView.setVisibility(VISIBLE);
                mHintView.setText(R.string.xlistview_footer_hint_ready1);
                break;
        }
    }

    public void setTextColor(int color)
    {
        mHintView.setTextColor(color);
    }
}