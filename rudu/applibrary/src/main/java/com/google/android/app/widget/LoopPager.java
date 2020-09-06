package com.google.android.app.widget;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;


import com.google.android.app.utils.Reflect;
import com.google.android.app.utils.ViewUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LoopPager extends RelativeLayout implements OnPageChangeListener {

    protected Context mContext = null;
    public ViewPager viewPager = null;
    protected boolean mIsContinue = true;
    protected AtomicInteger what = new AtomicInteger(0);
    private boolean mDragged = false;

    AdvAdapter bannerPagerAdapter;
    public int switchInterval = 5000;
    boolean initilized = false;
    //是否允许轮滑
    public boolean allowLoopTouch = true;
    //是否允许自动切换
    public boolean autoSwitch = false;

    public LoopPager(Context context) {
        this(context, null);
        mContext = context;
    }

    public LoopPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        //		super(context, attrs, defStyleAttr, defStyleRes);
        this(context, null);
        mContext = context;
    }

    public LoopPager(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, null);
        mContext = context;
    }

    public LoopPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        inflate();
    }


    public void inflate() {
        if (initilized)
            return;
        initilized = true;
        BannerViewPager pg=new BannerViewPager(mContext);
        this.addView(pg);
        viewPager = pg;
        viewPager.setOffscreenPageLimit(5);
        mViewHandler = new MyHandler(viewPager);
    }

    public ViewPager getViewPager()
    {
        return viewPager;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= 24)
            return;
//        clearCache();
    }

    protected void clearCache() {
        if (mViewHandler != null) {
            mViewHandler.removeMessages(0);
        }
        if(mItems!=null)
            mItems.clear();
        cleanAll();
        isFirst = true;
        System.gc();
    }



    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public void addItem(int index, Object data)
    {
        ImageItem imageItem=new ImageItem();
        imageItem.index=index;
        imageItem.data=data;
        mItems.add(imageItem);
    }





    public void cleanAll() {
        viewPager.removeAllViews();
        try {
            if (mViewHandler != null)
                mViewHandler.removeMessages(0);
            if (mItems != null)
                mItems.clear();
            if (bannerPagerAdapter != null)
                bannerPagerAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public class ImageItem
    {
        public Object data;
        public int index;
    }

    protected ArrayList<ImageItem> mItems = new ArrayList<ImageItem>();
    protected OnItemClickListener mListener = null;

    public List<ImageItem> getItems() {
        return mItems;
    }


    public void updateLayout() {
        if (mItems == null || mItems.size() == 0)
            return;
        if (mItems.size() > 1 && allowLoopTouch)
            initPagerExtraPrev_End();
        if (bannerPagerAdapter == null)
            bannerPagerAdapter = new AdvAdapter(mItems);
        else
            bannerPagerAdapter.views = mItems;
        viewPager.setAdapter(bannerPagerAdapter);
        viewPager.getAdapter().notifyDataSetChanged();
        if(mItems.size()==1)
        {
            autoSwitch = false;
            if(onLooperPagerHandle!=null)
            {
                onLooperPagerHandle.onLoopPageSelectedReallyReallyIndex(0);
                onLooperPagerHandle.onLoopPageSelected(0);
            }
        }
        viewPager.addOnPageChangeListener(this);
        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mViewHandler.removeMessages(0);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mDragged = false;
                    case MotionEvent.ACTION_MOVE:
                        mIsContinue = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (autoSwitch) {
                            Message message = mViewHandler.obtainMessage();
                            message.arg1 = -1;
                            message.what = 0;
                            mViewHandler.sendMessageDelayed(message, switchInterval);
                        }
                    case MotionEvent.ACTION_CANCEL:
                    default:
                        mIsContinue = true;
                        break;
                }
                return false;
            }
        });
        if (mItems.size() > 1 && allowLoopTouch) { //多于1个，才循环并开启定时器
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(1, true); //将viewPager初始位置设置为1
                    autoSwitchHandle();
                }
            },300);
        } else {
            viewPager.setCurrentItem(0);
            autoSwitchHandle();
        }

    }

    /**
     * 初始化第一张 后最后一张
     */
    protected void initPagerExtraPrev_End() {
        ImageItem bufferStart=mItems.get(0);
        ImageItem bufferEnd = mItems.get(mItems.size() - 1);
        mItems.add(0, bufferEnd);
        mItems.add(bufferStart);
    }

    boolean isFirst = true;


    protected Handler mViewHandler = null;

    protected class MyHandler extends Handler {
        WeakReference<ViewPager> viewPager;
        Reflect reflect;
        MyHandler(ViewPager pager) {
            viewPager = new WeakReference<ViewPager>(pager);
        }

        @Override
        public void handleMessage(Message msg) {
            if(reflect==null)
            {
                reflect= Reflect.on(ViewUtils.searchTintContextHostActivity(getContext()));
            }
            boolean mResumed=reflect.get("mResumed");
            if(!mResumed)
            {
                autoSwitchHandle();
                return;
            }
            if (msg.arg1 >= 0)
                viewPager.get().setCurrentItem(msg.arg1);
            if (mDragged && !mIsContinue)
                return;
            Message message = mViewHandler.obtainMessage();
            if (allowLoopTouch&&viewPager.get().isAttachedToWindow())
                message.arg1 = viewPager.get().getCurrentItem() + 1;
            else {
                if (viewPager.get().getCurrentItem() >= mItems.size() - 1)
                    message.arg1 = 0;
                else
                    message.arg1 = viewPager.get().getCurrentItem() + 1;
            }
            message.what = 0;
            mViewHandler.sendMessageDelayed(message, switchInterval);
            super.handleMessage(msg);
        }
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
        mDragged = true;
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }



    @Override
    public void onPageSelected(int index)
    {
        if(mItems.size()>1&&allowLoopTouch)
        {
            int position = index;
            if(index==1||index>=mItems.size() - 1)
            {
                position=0;
            }
            else if(index==0)
            {
                position=mItems.size() - 3;
            }
            else
            {
                position=index-1;
            }
            if(onLooperPagerHandle!=null)
            {
                onLooperPagerHandle.onLoopPageSelectedReallyReallyIndex(position);
                onLooperPagerHandle.onLoopPageSelected(index);
            }
        }
        else if(onLooperPagerHandle!=null)
        {
            onLooperPagerHandle.onLoopPageSelectedReallyReallyIndex(index);
        }
        final int delayCall = 300;
        if (mItems.size() > 1 && allowLoopTouch) { //多于1，才会循环跳转
            if (index < 1) //首位之前，跳转到末尾（N）
            {
                final int postion = mItems.size() - 2;
                mViewHandler.removeMessages(0);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mViewHandler.removeMessages(0);
                        viewPager.setCurrentItem(postion, false);
                        autoSwitchHandle();
                    }
                }, delayCall);

            } else if (index >= mItems.size() - 1) { //末位之后，跳转到首位（1）
                final int postion = 1;
                mViewHandler.removeMessages(0);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mViewHandler.removeMessages(0);
                        viewPager.setCurrentItem(postion, false);
                        autoSwitchHandle();
                    }
                }, delayCall);
            }
            else {
                autoSwitchHandle();
            }
        }
        else
        {
            autoSwitchHandle();
        }

    }

    void autoSwitchHandle()
    {
        if (mItems.size() > 1&&autoSwitch)
        {
            Message message = mViewHandler.obtainMessage();
            message.arg1 = viewPager.getCurrentItem() + 1;
            message.what = 0;
            mViewHandler.sendMessageDelayed(message, switchInterval);
        }
    }

    protected final class AdvAdapter extends PagerAdapter {
        protected List<ImageItem> views = null;

        public AdvAdapter(List<ImageItem> views) {
            this.views = views;
        }

        @Override
        public void destroyItem(View view, int index, Object arg2) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object)
        {
            super.setPrimaryItem(container, position, object);
            try
            {
                if(onLooperPagerHandle!=null)
                    onLooperPagerHandle.displayImage(mContext,views.get(position), (View) object);
            }
            catch (Exception e) {
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int index)
        {
            try
            {
                if(onLooperPagerHandle!=null)
                {
                    View view=onLooperPagerHandle.createImageView(mContext,mItems.get(index));
                    view.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                mListener.onClick(LoopPager.this, mItems.get(index).index, null);
                            } catch (Exception e) {
                            }
                        }
                    });
                    container.addView(view);
                    return view;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }


    public OnLooperPagerHandle onLooperPagerHandle;

    public interface OnItemClickListener {
        void onClick(LoopPager banner, int position, Object tag);
    }

    public interface OnLooperPagerHandle
    {
        void displayImage(Context context, ImageItem path, View view);
        View createImageView(Context context,ImageItem position);

        /**
         * 要按外部真实数据索引就用他
         * @param index
         */
        void onLoopPageSelectedReallyReallyIndex(int index);

        /**
         * 这个是viewpager里面的实际索引  可以循环的时候这个索引其实就是currentItem
         * @param index
         */
        void onLoopPageSelected(int index);
    }
}