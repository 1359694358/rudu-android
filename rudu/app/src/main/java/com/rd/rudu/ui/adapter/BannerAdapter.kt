package com.rd.rudu.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.DefaultLayoutHelper
import com.google.android.app.adapter.AppVLayoutAdapter
import com.google.android.app.adapter.BaseViewHolder
import com.rd.rudu.R
import com.rd.rudu.databinding.AdapterJoinBannerBinding

class BannerAdapter(context: Context, layoutHelper: LayoutHelper= DefaultLayoutHelper.newHelper(1)) :
    AppVLayoutAdapter<String>(context, layoutHelper)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
       return BannerViewHolder(R.layout.adapter_join_banner,context)
    }
}

class BannerViewHolder(layoutId: Int, context: Context?) :
    BaseViewHolder<AdapterJoinBannerBinding>(layoutId, context)
