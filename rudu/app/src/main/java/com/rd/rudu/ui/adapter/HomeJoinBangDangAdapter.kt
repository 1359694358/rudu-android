package com.rd.rudu.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.app.adapter.BaseRecyclerAdapter
import com.google.android.app.adapter.BaseViewHolder
import com.rd.rudu.R
import com.rd.rudu.databinding.AdapterJoinBangdangItemBinding

class HomeJoinBangDangAdapter(context: Context) : BaseRecyclerAdapter<String>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JoinBangDangItemHolder(R.layout.adapter_join_bangdang_item,context)
    }
}

class JoinBangDangItemHolder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinBangdangItemBinding>(layoutId, context)