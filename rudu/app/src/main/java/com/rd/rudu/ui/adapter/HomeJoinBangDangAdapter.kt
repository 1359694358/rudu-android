package com.rd.rudu.ui.adapter

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.recyclerview.widget.RecyclerView
import com.google.android.app.adapter.BaseRecyclerAdapter
import com.google.android.app.adapter.BaseViewHolder
import com.rd.rudu.R
import com.rd.rudu.bean.result.JoinBlastResultBean
import com.rd.rudu.databinding.AdapterJoinBangdangItemBinding

class HomeJoinBangDangAdapter(context: Context) : BaseRecyclerAdapter<JoinBlastResultBean.JoinBlastResultItem>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JoinBangDangItemHolder(R.layout.adapter_join_bangdang_item,context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if(holder !is JoinBangDangItemHolder)
            return
        holder.contentViewBinding.prizeIcon.visibility= View.VISIBLE
        when(position)
        {
            0->
            {
                holder.contentViewBinding.prizeIcon.setImageResource(R.mipmap.icon_first)
            }
            1->
            {
                holder.contentViewBinding.prizeIcon.setImageResource(R.mipmap.icon_second)
            }
            2->
            {
                holder.contentViewBinding.prizeIcon.setImageResource(R.mipmap.icon_third)
            }
            else->
            {
                holder.contentViewBinding.prizeIcon.visibility= View.GONE
            }
        }

        var currentPrice="99"
        var originPrice="139.9"
        var priceFormat=SpannableStringBuilder(context.resources.getString(R.string.price_format,currentPrice,originPrice))
        priceFormat[0,1]=AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.dimen10))
        priceFormat[1,currentPrice.length+1]=AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.dimen13))
        priceFormat[0,currentPrice.length+1]=ForegroundColorSpan(ContextCompat.getColor(context,R.color.currentPriceColor))
        priceFormat[priceFormat.length-originPrice.length-1,priceFormat.length]=ForegroundColorSpan(ContextCompat.getColor(context,R.color.originPriceColor))
        priceFormat[priceFormat.length-originPrice.length-1,priceFormat.length]=AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.dimen10))
        priceFormat[priceFormat.length-originPrice.length-1,priceFormat.length]=StrikethroughSpan()

        holder.contentViewBinding.priceInfo.text=priceFormat
    }
}

class JoinBangDangItemHolder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinBangdangItemBinding>(context, layoutId)