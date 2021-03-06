package com.rd.rudu.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chinamcloud.project.shanshipin.utils.bindMagicIndicator
import com.google.android.app.adapter.FragmentAdapter
import com.google.android.app.utils.StatusBarUtil
import com.google.android.app.widget.BaseFragment
import com.rd.rudu.R
import com.rd.rudu.databinding.FragmentHomeTopnewsBinding
import com.rd.rudu.utils.ListPlayerUtil
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView


class HomeTopNewsFragment: BaseFragment<FragmentHomeTopnewsBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_home_topnews
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTab()
    }
    fun setTab()
    {
        var tab=contentBinding.tabLayout
        contentBinding.tabLayout.viewTreeObserver.addOnDrawListener {
            if(contentBinding.tabLayout.layoutParams!=null&&contentBinding.tabLayout.layoutParams is ViewGroup.MarginLayoutParams)
            {
                (contentBinding.tabLayout.layoutParams as ViewGroup.MarginLayoutParams).topMargin=
                    StatusBarUtil.getStatusBarHeight(requireActivity())
                contentBinding.tabLayout.requestLayout()
            }
        }
        var color=Pair(0xFF057CE4.toInt(),0xFF222222.toInt())
        var titles=arrayOf("资讯头条","视频专区")
        var tabNavigatorAdapter= CommonNavigator(requireContext())
        tabNavigatorAdapter.isAdjustMode=true
        tabNavigatorAdapter.adapter=object : CommonNavigatorAdapter()
        {
            override fun getTitleView(context: Context, index: Int): IPagerTitleView
            {
                val titleView= SimplePagerTitleView(context)
                titleView.selectedColor=color.first
                titleView.normalColor=color.second
                titleView.text=titles[index]
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.resources.getDimension(R.dimen.dimen14))
                titleView.setOnClickListener {
                    contentBinding.viewPager.currentItem=index
                }
                return titleView
            }

            override fun getCount(): Int {
                return titles.size
            }

            override fun getIndicator(context: Context): IPagerIndicator
            {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineWidth=context.resources.getDimension(R.dimen.dimen92)
                indicator.lineHeight=context.resources.getDimension(R.dimen.dimen3)
                indicator.roundRadius=context.resources.getDimension(R.dimen.dimen3)
                indicator.setColors(color.first)
                return indicator
            }

        }
        tab.navigator=tabNavigatorAdapter
        val fgarr= arrayListOf<Fragment>(HomeTopNewsListFragment(),HomeVideoNewsListFragment())
        contentBinding.viewPager.adapter=FragmentAdapter(childFragmentManager,fgarr)
        contentBinding.viewPager.addOnPageChangeListener(object :ViewPager.SimpleOnPageChangeListener()
        {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position==0)
                {
                    ListPlayerUtil.stopPlay()
                }
            }
        })
        ViewPagerHelper.bind(tab,  contentBinding.viewPager)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(hidden)
            ListPlayerUtil.stopPlay()
    }
}