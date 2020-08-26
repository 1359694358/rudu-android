package com.google.android.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;
/**
 * Banner用的ViewPager
 * @author x
 *
 */
public class BannerViewPager extends ViewPager {
	public boolean whenMoreThan1Intercept=false;
	public BannerViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BannerViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent arg0) {
		if(whenMoreThan1Intercept&&getAdapter()!=null&&getAdapter().getCount()>1)
			getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(arg0);
	}

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		getParent().requestDisallowInterceptTouchEvent(true);
//		return super.dispatchTouchEvent(ev);
//	}

}
