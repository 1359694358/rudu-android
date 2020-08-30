package com.rd.rudu.ui.adapter

import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.style.*
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.app.adapter.BaseRecyclerAdapter
import com.google.android.app.adapter.BaseRecyclerAdapter.LayoutSpanCount
import com.google.android.app.adapter.BaseRecyclerAdapter.OnlyOneSpan
import com.google.android.app.adapter.BaseViewHolder
import com.google.android.app.widget.LoopPager
import com.google.android.app.widget.RadiusBackgroundSpan
import com.google.android.app.widget.RoundBackgroundColorSpan
import com.rd.rudu.R
import com.rd.rudu.databinding.*

interface HomeJoinItemType
{
    fun getJoinItemType():Pair<Int,Int>
}

class HomeJoinItem(var joinType: Pair<Int,Int>):HomeJoinItemType
{

    override fun getJoinItemType(): Pair<Int, Int> {
        return joinType
    }

}

val JOIN_TYPE_BANNER=Pair(1,LayoutSpanCount)
val JOIN_TYPE_IMAGE1=Pair(2,LayoutSpanCount)
val JOIN_TYPE_IMAGE2=Pair(3,LayoutSpanCount)
val JOIN_TYPE_INTRO=Pair(4,LayoutSpanCount)
val JOIN_TYPE_ZHANHUI_HEADER=Pair(5,LayoutSpanCount)
val JOIN_TYPE_ZHANHUI_ITEM=Pair(6,OnlyOneSpan)
val JOIN_TYPE_BANGDANG=Pair(7,LayoutSpanCount)
val JOIN_TYPE_HaoHuoTuiJianHeader=Pair(8,LayoutSpanCount)
val JOIN_TYPE_HaoHuoTuiJianItem=Pair(9,LayoutSpanCount)
val JOIN_TYPE_XinXianChangHeader=Pair(10,LayoutSpanCount)
val JOIN_TYPE_XinXianChangItem=Pair(11,OnlyOneSpan)


fun buildJoinList():List<HomeJoinItemType>
{
    var list= mutableListOf<HomeJoinItemType>()
    list.add(HomeJoinItem(JOIN_TYPE_BANNER))
    list.add(HomeJoinItem(JOIN_TYPE_IMAGE1))
    list.add(HomeJoinItem(JOIN_TYPE_IMAGE2))
    list.add(HomeJoinItem(JOIN_TYPE_IMAGE2))
    list.add(HomeJoinItem(JOIN_TYPE_INTRO))
    list.add(HomeJoinItem(JOIN_TYPE_ZHANHUI_HEADER))
    list.add(HomeJoinItem(JOIN_TYPE_ZHANHUI_ITEM))
    list.add(HomeJoinItem(JOIN_TYPE_ZHANHUI_ITEM))
    list.add(HomeJoinItem(JOIN_TYPE_BANGDANG))
    list.add(HomeJoinItem(JOIN_TYPE_BANGDANG))
    list.add(HomeJoinItem(JOIN_TYPE_HaoHuoTuiJianHeader))
    list.add(HomeJoinItem(JOIN_TYPE_HaoHuoTuiJianItem))
    list.add(HomeJoinItem(JOIN_TYPE_HaoHuoTuiJianItem))
    list.add(HomeJoinItem(JOIN_TYPE_HaoHuoTuiJianItem))
    list.add(HomeJoinItem(JOIN_TYPE_XinXianChangHeader))
    list.add(HomeJoinItem(JOIN_TYPE_XinXianChangItem))
    list.add(HomeJoinItem(JOIN_TYPE_XinXianChangItem))
    return list
}

class CompanyItemHolder(layoutId: Int, context: Context) : BaseViewHolder<AdapterJoinCompanyBinding>(layoutId, context)

class BannerViewHolder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinBannerBinding>(layoutId, context), LoopPager.OnLooperPagerHandle {
    init {
        contentViewBinding.banner.onLooperPagerHandle=this
        contentViewBinding.banner.setOnItemClickListener { banner, position, tag ->

        }
    }

    fun setData()
    {
        if(contentViewBinding.banner.items.size>0)
            return
        contentViewBinding.banner.cleanAll()
        contentViewBinding.banner.addItem(0, Any())
        contentViewBinding.banner.addItem(1,Any())
        contentViewBinding.banner.addItem(2,Any())
        contentViewBinding.indicator.removeAllViews()
        for(index in 0 until contentViewBinding.banner.items.size)
        {
            var view=ImageView(context)
            view.setImageResource(R.drawable.join_banner_indicator_selector)
            var lp=LinearLayout.LayoutParams(-2,-2)
            lp.marginStart=context.resources.getDimensionPixelOffset(R.dimen.dimen2)
            lp.marginEnd=context.resources.getDimensionPixelOffset(R.dimen.dimen2)
            contentViewBinding.indicator.addView(view,lp)
            if(index==0)
                view.isSelected=true
        }
        contentViewBinding.banner.updateLayout()
    }

    override fun createImageView(context: Context?, position: LoopPager.ImageItem?): View
    {
        var itemView=AdapterJoinBannerItemBinding.inflate(getLayoutInflater(),null,false)
        displayImage(context,position,itemView.root)
        return itemView.root
    }

    override fun onLoopPageSelected(index: Int) {
    }

    override fun displayImage(context: Context?, path: LoopPager.ImageItem?, view: View?)
    {
        var binding:AdapterJoinBannerItemBinding?=DataBindingUtil.getBinding(view!!)

    }

    override fun onLoopPageSelectedReallyReallyIndex(position:Int)
    {
        for(index in 0 until contentViewBinding.indicator.childCount)
        {
            var view= contentViewBinding.indicator.getChildAt(index)
            view.isSelected=index==position
            view.layoutParams.width=-2
            view.layoutParams.height=-2
            view.requestLayout()
        }
    }
}

class JoinImage2Holder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinImageBinding>(layoutId, context)

class JoinIntroHolder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinIntroBinding>(layoutId, context)

class JoinZhanHuiHeaderHolder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinGridHeaderBinding>(layoutId, context)

class JoinZhanHuiItemHolder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinGridItemBinding>(layoutId, context)

class JoinBangDangHolder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinBangdangBinding>(layoutId, context)
{
    val adapter:HomeJoinBangDangAdapter by lazy { HomeJoinBangDangAdapter(context) }
    fun setData()
    {
        adapter.data.clear()
        adapter.data.add("1")
        adapter.data.add("1")
        adapter.data.add("1")
        adapter.data.add("1")
        adapter.data.add("1")
        contentViewBinding.bangdangList.adapter=adapter
    }
}

class HomeJoinHaoHuoTuiJianHolder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinTuijianItemBinding>(layoutId, context)
{
    fun setData()
    {
        var currentPrice="99"
        var originPrice="139.9"
        var priceFormat= SpannableStringBuilder(context.resources.getString(R.string.price_format,currentPrice,originPrice))
        priceFormat[0,1]= AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.dimen10))
        priceFormat[1,currentPrice.length+1]=AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.dimen15))
        priceFormat[0,currentPrice.length+1]=ForegroundColorSpan(ContextCompat.getColor(context,R.color.currentPriceColor))
        priceFormat[priceFormat.length-originPrice.length-1,priceFormat.length]=ForegroundColorSpan(ContextCompat.getColor(context,R.color.originPriceColor))
        priceFormat[priceFormat.length-originPrice.length-1,priceFormat.length]=AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.dimen12))
        priceFormat[priceFormat.length-originPrice.length-1,priceFormat.length]=StrikethroughSpan()
        contentViewBinding.priceInfo.text=priceFormat

        var title="客厅瓷砖地砖通体大理石瓷砖仿玛瑙玉餐厅地板定制"
        var titleFormat=SpannableStringBuilder("推荐 ${title}")
        titleFormat[0,2]= RoundBackgroundColorSpan(context,0xFFFF653C.toInt(), Color.WHITE)//,context.resources.getDimensionPixelOffset(R.dimen.dimen4),context.resources.getDimensionPixelOffset(R.dimen.dimen8))
        titleFormat[0,2]= AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.dimen10))
        titleFormat[titleFormat.length-title.length,titleFormat.length]= AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.dimen13))
        contentViewBinding.title.text=titleFormat
    }
}

class HomeJoinMiddleTitleHolder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinMiddletitleBinding>(layoutId, context)

class HomeJoinChangXianItemHolder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinChangxianItemBinding>(layoutId, context)
{
    fun setData()
    {
        var currentPrice="99"
        var originPrice="139.9"
        var priceFormat= SpannableStringBuilder(context.resources.getString(R.string.price_format,currentPrice,originPrice))
        priceFormat[0,1]= AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.dimen12))
        priceFormat[1,currentPrice.length+1]=AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.dimen17))
        priceFormat[0,currentPrice.length+1]=ForegroundColorSpan(ContextCompat.getColor(context,R.color.currentPriceColor))
        priceFormat[priceFormat.length-originPrice.length-1,priceFormat.length]=ForegroundColorSpan(ContextCompat.getColor(context,R.color.originPriceColor))
        priceFormat[priceFormat.length-originPrice.length-1,priceFormat.length]=AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.dimen12))
        priceFormat[priceFormat.length-originPrice.length-1,priceFormat.length]=StrikethroughSpan()
        contentViewBinding.priceInfo.text=priceFormat

        var title="客厅瓷砖地砖通体大理石瓷砖仿玛瑙玉餐厅地板定制"
        var titleFormat=SpannableStringBuilder("新品 ${title}")
        titleFormat[0,2]= RoundBackgroundColorSpan(context,0xFF81BDF1.toInt(), Color.WHITE)//,context.resources.getDimensionPixelOffset(R.dimen.dimen4),context.resources.getDimensionPixelOffset(R.dimen.dimen8))
        titleFormat[0,2]= AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.dimen10))
        titleFormat[titleFormat.length-title.length,titleFormat.length]= AbsoluteSizeSpan(context.resources.getDimensionPixelSize(R.dimen.dimen13))
        contentViewBinding.title.text=titleFormat
    }
}

class HomeJoinListAdapter(context: Context) : BaseRecyclerAdapter<HomeJoinItemType>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType)
        {
            JOIN_TYPE_BANNER.first->
                BannerViewHolder(R.layout.adapter_join_banner,context)
            JOIN_TYPE_IMAGE1.first->
                CompanyItemHolder(R.layout.adapter_join_company,context)
            JOIN_TYPE_IMAGE2.first->
                JoinImage2Holder(R.layout.adapter_join_image,context)
            JOIN_TYPE_INTRO.first->
                JoinIntroHolder(R.layout.adapter_join_intro,context)
            JOIN_TYPE_ZHANHUI_HEADER.first,JOIN_TYPE_HaoHuoTuiJianHeader.first->
                JoinZhanHuiHeaderHolder(R.layout.adapter_join_grid_header,context)
            JOIN_TYPE_BANGDANG.first->
                JoinBangDangHolder(R.layout.adapter_join_bangdang,context)
            JOIN_TYPE_ZHANHUI_ITEM.first->
                JoinZhanHuiItemHolder(R.layout.adapter_join_grid_item,context)
            JOIN_TYPE_HaoHuoTuiJianItem.first->
                HomeJoinHaoHuoTuiJianHolder(R.layout.adapter_join_tuijian_item,context)
            JOIN_TYPE_XinXianChangHeader.first->
                HomeJoinMiddleTitleHolder(R.layout.adapter_join_middletitle,context)
            JOIN_TYPE_XinXianChangItem.first->
                HomeJoinChangXianItemHolder(R.layout.adapter_join_changxian_item,context)
            else->
                object:RecyclerView.ViewHolder(View(context)){}
        }
        /*  if(viewType== JOIN_TYPE_IMAGE1.first)
              return CompanyItemHolder(R.layout.adapter_join_company,context)*/
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var viewType=getItemViewType(position)
        when(holder)
        {
            is JoinZhanHuiHeaderHolder->
            {
                if(JOIN_TYPE_HaoHuoTuiJianHeader.first==viewType)
                {
                    holder.contentViewBinding.jianjie.text="好货推荐"
                }
            }
            is JoinBangDangHolder->
            {
                holder.setData()
            }
            is BannerViewHolder->
            {
                holder.setData()
            }
            is HomeJoinMiddleTitleHolder->
            {
                if(JOIN_TYPE_XinXianChangHeader.first==viewType)
                {
                    holder.contentViewBinding.title.text="新品尝鲜"
                    holder.contentViewBinding.title.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(context,R.mipmap.icon_xinpin),null,null,null)
                }
            }
            is HomeJoinHaoHuoTuiJianHolder->
            {
                holder.setData()
            }
            is HomeJoinChangXianItemHolder->
            {
                holder.setData()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getJoinItemType().first
    }

    override fun getItemSpanCount(position: Int): Int {
        return getItem(position).getJoinItemType().second
    }
}