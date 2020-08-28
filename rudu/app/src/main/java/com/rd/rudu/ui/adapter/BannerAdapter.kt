package com.rd.rudu.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.DefaultLayoutHelper
import com.google.android.app.adapter.AppVLayoutAdapter
import com.google.android.app.adapter.BaseViewHolder
import com.google.android.app.widget.LoopPager
import com.rd.rudu.R
import com.rd.rudu.databinding.AdapterJoinBannerBinding

class BannerAdapter(context: Context, layoutHelper: LayoutHelper= DefaultLayoutHelper.newHelper(1)) :
    AppVLayoutAdapter<String>(context, layoutHelper)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
       return BannerViewHolder(R.layout.adapter_join_banner,context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if(holder is BannerViewHolder)
        {
            holder.setData()
        }
    }
}

class BannerViewHolder(layoutId: Int, context: Context?) :
    BaseViewHolder<AdapterJoinBannerBinding>(layoutId, context), LoopPager.OnLooperPagerHandle {
    init {
        contentViewBinding.banner.onLooperPagerHandle=this
        contentViewBinding.banner.setOnItemClickListener { banner, position, tag ->

        }
    }

    fun setData()
    {
    }

    override fun createImageView(context: Context?, position: LoopPager.ImageItem?): View
    {
        TODO("Not yet implemented")
    }

    override fun onLoopPageSelected(index: Int) {
    }

    override fun displayImage(context: Context?, path: LoopPager.ImageItem?, view: View?)
    {
        TODO("Not yet implemented")
    }

    override fun onLoopPageSelectedReallyReallyIndex(index: Int)
    {
    }
}
