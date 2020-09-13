package com.google.android.app.adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.app.utils.Reflect;


public class FragmentAdapter extends FragmentStatePagerAdapter {

    List<Fragment> list;
    private FragmentManager fm;

    Reflect reflect;
    public boolean needDestory = true;

    public FragmentAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
        this.fm = fm;
        reflect = Reflect.on(this);
    }

    public List<Fragment> getList() {
        return list;
    }
	/*@Override
	public Fragment instantiateItem(ViewGroup container, int position)
	{
		ArrayList<Fragment> mFragments=reflect.get("mFragments");
		if (mFragments.size() > position) {
			Fragment f = mFragments.get(position);
			if (f != null) {
				return f;
			}
		}
		FragmentTransaction mCurTransaction=reflect.get("mCurTransaction");
		if (mCurTransaction == null) {
			mCurTransaction = fm.beginTransaction();
			reflect.set("mCurTransaction",mCurTransaction);
		}

		Fragment fragment = getItem(position);
		ArrayList<Fragment.SavedState> mSavedState=reflect.get("mSavedState");
		if (mSavedState.size() > position) {
			Fragment.SavedState fss = mSavedState.get(position);
			if (fss != null) {
				fragment.setInitialSavedState(fss);
			}
		}
		while (mFragments.size() <= position) {
			mFragments.add(null);
		}
		fragment.setMenuVisibility(false);
		fragment.setUserVisibleHint(false);
		mFragments.set(position, fragment);
		if(TextUtils.isEmpty(fragment.getTag())&&!fragment.isAdded())
			mCurTransaction.add(container.getId(), fragment,fragment.toString());

		return fragment;
	}*/

    @Override
    public Fragment getItem(int paramInt) {
        if (paramInt >= list.size())
            return null;
        Fragment fragment = list.get(paramInt);

        return fragment;
    }

    boolean isReplaced=false;
    public void setItem(int position, Fragment fragment) {
        Fragment old = getItem(position);
        if (old != fragment)
        {
            removeItem(position);
            super.destroyItem(null, position, old);
        }
        list.set(position, fragment);
        isReplaced=true;
//        notifyDataSetChanged();
    }


    public void removeItem(int position) {
        try {
            Fragment old = getItem(position);
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(old);
            ft.commitAllowingStateLoss();
            fm.executePendingTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void removeAllItem() {
        for (int i = 0; i < getCount(); i++)
            removeItem(i);
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        return object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (needDestory)
            super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }

  /*  @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList != null && position > -1 && position < titleList.size())
            return titleList.get(position).getTitle();
        return super.getPageTitle(position);
    }*/

	/*@Override
	public void finishUpdate(ViewGroup container) {
		FragmentTransaction mCurTransaction=reflect.get("mCurTransaction");
		if (mCurTransaction != null) {
			mCurTransaction.commitAllowingStateLoss();
			reflect.set("mCurTransaction",null);
		}
	}*/

    Reflect viewPagerReflect;

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (isReplaced&&container != null && container instanceof ViewPager) {
            if (viewPagerReflect == null) {
                viewPagerReflect = Reflect.on(container);
            }
            ArrayList<Object> mItems = viewPagerReflect.get("mItems");
            for(Object itemInfo:mItems)
            {
                Reflect itemInfoReflect=Reflect.on(itemInfo);
                int itemPosition=itemInfoReflect.get("position");
                itemInfoReflect.set("offset",itemPosition*1.0F);
            }
            isReplaced=false;
        }
        super.setPrimaryItem(container, position, object);
    }
}