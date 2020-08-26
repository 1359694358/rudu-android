package com.google.android.app.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class XSmartRefreshLayout extends SmartRefreshLayout
{
    Context mContext;
    BasicStyleHeader header;
    BasicStyleFooter footer;
    public XSmartRefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public XSmartRefreshLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }


    private void init(Context context)
    {
        mContext=context;
        header=new BasicStyleHeader(mContext);
        footer=new BasicStyleFooter(mContext);
        setRefreshHeader(header);
        setRefreshFooter(footer);
        setEnableAutoLoadMore(true);
        setEnableOverScrollBounce(true);//越界回弹功能
        //内容不满一页时开启上拉加载功能
        setEnableScrollContentWhenLoaded(true);
        setEnableFooterFollowWhenLoadFinished(true);
        setHeaderTriggerRate(0.25f);
        setFooterTriggerRate(0.25f);
    }

    public void setHeaderTextColor(int color)
    {
        header.setTextColor(color);
    }

    public void setFooterTextColor(int color)
    {
        footer.setTextColor(color);
    }

}
