package com.rd.rudu.ui.fragment

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.DefaultLayoutHelper
import com.google.android.app.adapter.AppVLayoutAdapter

class BannerAdapter(context: Context, layoutHelper: LayoutHelper= DefaultLayoutHelper.newHelper(1)) :
    AppVLayoutAdapter<String>(context, layoutHelper)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        TODO("Not yet implemented")
    }
}