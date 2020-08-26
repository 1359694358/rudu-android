package com.google.android.app.widget;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.app.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BasicStyleHeader extends RelativeLayout implements RefreshHeader
{
    Context mContext;
    private ImageView mArrowImageView;
    private ProgressBar mProgressBar;
    TextView refresh_time;
    TextView hintText,lastedTxt;
    private final int ROTATE_ANIM_DURATION = 180;

    public BasicStyleHeader(Context context)
    {
        super(context);
        init(context);
    }

    protected void init(Context context)
    {
        mContext=context;
        LayoutInflater.from(mContext).inflate(R.layout.classic_header,this);
        lastedTxt=findViewById(R.id.lastedTxt);
        mArrowImageView =  findViewById(R.id.header_arrow);
        refresh_time=findViewById(R.id.refresh_time);
        hintText= findViewById(R.id.header_hint);
        mProgressBar =  findViewById(R.id.header_progressbar);
        refreshTime();
    }
    void refreshTime()
    {
        SimpleDateFormat formatter   =   new   SimpleDateFormat   (getResources().getString(R.string.xlistview_header_timeformat));
        Date curDate   =   new   Date(System.currentTimeMillis());
        //获取当前时间
        String   str   =   formatter.format(curDate);
        refresh_time.setText(str);
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
        refreshTime();
        if (success)
        {
            hintText.setText("刷新完成");
        } else {
            hintText.setText("刷新失败");
        }
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
        switch (newState) {
            case None:
            case PullDownToRefresh:
                hintText.setText(R.string.xlistview_header_hint_normal1);
                mProgressBar.setVisibility(GONE);
                mArrowImageView.setVisibility(VISIBLE);
              /*  mArrowView.setVisibility(VISIBLE);//显示下拉箭头
                mProgressView.setVisibility(GONE);//隐藏动画
                mArrowView.animate().rotation(0);//还原箭头方向*/
                mArrowImageView.clearAnimation();
                mArrowImageView.animate().rotation(0);
                break;
            case Refreshing:
                hintText.setText(R.string.xlistview_header_hint_loading1);
              /*  mProgressView.setVisibility(VISIBLE);//显示加载动画
                mArrowView.setVisibility(GONE);//隐藏箭头*/
                mProgressBar.setVisibility(VISIBLE);
                mArrowImageView.setVisibility(GONE);
                break;
            case ReleaseToRefresh:
                hintText.setText(R.string.xlistview_header_hint_ready1);
                mArrowImageView.animate().rotation(180);//显示箭头改为朝上
                break;
        }
    }

    public void setTextColor(int color)
    {
        refresh_time.setTextColor(color);
        hintText.setTextColor(color);
        lastedTxt.setTextColor(color);
    }
}