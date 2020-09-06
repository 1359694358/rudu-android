package com.rd.rudu.ui.adapter

import android.content.Context
import android.content.Intent
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
import com.google.android.app.widget.RoundBackgroundColorSpan
import com.rd.rudu.R
import com.rd.rudu.bean.result.*
import com.rd.rudu.databinding.*
import com.rd.rudu.ui.activity.*
import org.jetbrains.anko.collections.forEachByIndex
import org.jetbrains.anko.startActivity

@JvmField
val JOIN_TYPE_BANNER=Pair(1,LayoutSpanCount)
@JvmField
val JOIN_TYPE_IMAGE1=Pair(2,LayoutSpanCount)
@JvmField
val JOIN_TYPE_JOINPARTNER=Pair(3,LayoutSpanCount)
@JvmField
val JOIN_TYPE_INVITESHOP=Pair(4,LayoutSpanCount)
@JvmField
val JOIN_TYPE_INTRO=Pair(5,LayoutSpanCount)
@JvmField
val JOIN_TYPE_ZHANHUI_HEADER=Pair(6,LayoutSpanCount)
@JvmField
val JOIN_TYPE_ZHANHUI_ITEM=Pair(7,OnlyOneSpan)
@JvmField
val JOIN_TYPE_BANGDANG=Pair(8,LayoutSpanCount)
@JvmField
val JOIN_TYPE_HaoHuoTuiJianHeader=Pair(9,LayoutSpanCount)
@JvmField
val JOIN_TYPE_HaoHuoTuiJianItem=Pair(10,LayoutSpanCount)
@JvmField
val JOIN_TYPE_XinXianChangHeader=Pair(11,LayoutSpanCount)
@JvmField
val JOIN_TYPE_XinXianChangItem=Pair(12,OnlyOneSpan)


interface HomeJoinItemType
{
    fun getJoinItemType():Pair<Int,Int>
}

class HomeJoinItem(var joinType: Pair<Int,Int>,var title:String?=""):HomeJoinItemType
{
    var imageResId:Int=0
    override fun getJoinItemType(): Pair<Int, Int> {
        return joinType
    }

}

fun buildJoinList():List<HomeJoinItemType>
{
    var list= mutableListOf<HomeJoinItemType>()
    list.add(HomeJoinItem(JOIN_TYPE_BANNER))
    list.add(HomeJoinItem(JOIN_TYPE_IMAGE1))
    var partener=HomeJoinItem(JOIN_TYPE_JOINPARTNER,"城市合伙人加盟")
    partener.imageResId=R.mipmap.city_partner_item
    list.add(partener)
    list.add(HomeJoinItem(JOIN_TYPE_INVITESHOP,"品牌招商入驻"))
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

class CompanyItemHolder(context: Context) : BaseViewHolder<AdapterJoinCompanyBinding>(
    context,
    R.layout.adapter_join_company
)
{
    init {
        itemView.setOnClickListener { itemView.context.startActivity<JoinCompanyActivity>(Pair(Intent.ACTION_ATTACH_DATA,contentViewBinding.data)) }
    }

    fun setData(data:JoinMerChantsBean)
    {
        contentViewBinding.data=data.data
    }
}

class BannerViewHolder(context: Context) :
    BaseViewHolder<AdapterJoinBannerBinding>(context, R.layout.adapter_join_banner), LoopPager.OnLooperPagerHandle {
    init {
        contentViewBinding.banner.onLooperPagerHandle=this
        contentViewBinding.banner.setOnItemClickListener { banner, position, tag ->
            var data=banner.items[position].data as JoinBannerResultBean.JoinBannerItem
            WebViewActivity.startActivity(itemView.context,data.linkUrl)
        }
    }

    fun setData(data:JoinBannerResultBean)
    {
        contentViewBinding.banner.cleanAll()
        data.data.forEachIndexed { index, joinBannerItem ->
            contentViewBinding.banner.addItem(index,joinBannerItem)
        }
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
        binding?.bannerItem= path!!.data as JoinBannerResultBean.JoinBannerItem?
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

class JoinPartnerHolder(context: Context) :
    BaseViewHolder<AdapterJoinPartnerBinding>(context, R.layout.adapter_join_partner)
{
    init {
        itemView.setOnClickListener {
            itemView.context.startActivity<JoinCityPartnerActivity>(Pair(Intent.ACTION_ATTACH_DATA,contentViewBinding.data))
        }
    }
    fun setData(data:JoinPartnerIntroResultBean)
    {
        contentViewBinding.data=data.data
    }
}

class InviteShopHolder(context: Context) :
    BaseViewHolder<AdapterJoinInviteshopBinding>(context, R.layout.adapter_join_inviteshop)
{
    init {
        itemView.setOnClickListener {
            itemView.context.startActivity<JoinBrandActivity>(Pair(Intent.ACTION_ATTACH_DATA, contentViewBinding.data))
        }
    }
    fun setData(data: JoinBrandInfoResultBean)
    {
        contentViewBinding.data=data.data
    }
}

class JoinIntroHolder(context: Context) :
    BaseViewHolder<AdapterJoinIntroBinding>(context, R.layout.adapter_join_intro)
{
    init {
        itemView.setOnClickListener {
            it.context.startActivity<RuduIntroActivity>()
        }
    }

    fun setData(data: JoinIntroInfoResultBean)
    {
        contentViewBinding.intro=data.data
    }
}
class JoinZhanHuiHeaderHolder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinGridHeaderBinding>(context, layoutId)

class JoinZhanHuiItemHolder(context: Context) :
    BaseViewHolder<AdapterJoinGridItemBinding>(context, R.layout.adapter_join_grid_item)
{
    init
    {
        itemView.setOnClickListener {
            it.context.startActivity<ExhibitionInfoActivity>()
        }
    }
    fun setData(data: JoinExhibitionResultBean.JoinExhibitionResult)
    {
        contentViewBinding.exhibition=data
    }
}
class JoinBangDangHolder(context: Context) :
    BaseViewHolder<AdapterJoinBangdangBinding>(context, R.layout.adapter_join_bangdang)
{
    val adapter:HomeJoinBangDangAdapter by lazy { HomeJoinBangDangAdapter(context) }
    fun setData(data:JoinBlastResultBean)
    {
        adapter.data.clear()
        adapter.data.addAll(data.data)
        adapter.notifyDataSetChanged()
        contentViewBinding.bangdangList.adapter=adapter
    }
}

class HomeJoinHaoHuoTuiJianHolder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinTuijianItemBinding>(context, layoutId)
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
    BaseViewHolder<AdapterJoinMiddletitleBinding>(context, layoutId)

class HomeJoinChangXianItemHolder(layoutId: Int, context: Context) :
    BaseViewHolder<AdapterJoinChangxianItemBinding>(context, layoutId)
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
                BannerViewHolder(context)
            JOIN_TYPE_IMAGE1.first->
                CompanyItemHolder(context)
            JOIN_TYPE_JOINPARTNER.first->
                JoinPartnerHolder(context)
            JOIN_TYPE_INVITESHOP.first->
                InviteShopHolder(context)
            JOIN_TYPE_INTRO.first->
                JoinIntroHolder(context)
            JOIN_TYPE_ZHANHUI_HEADER.first,JOIN_TYPE_HaoHuoTuiJianHeader.first->
                JoinZhanHuiHeaderHolder(R.layout.adapter_join_grid_header,context)
            JOIN_TYPE_BANGDANG.first->
                JoinBangDangHolder(context)
            JOIN_TYPE_ZHANHUI_ITEM.first->
                JoinZhanHuiItemHolder(context)
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
            is CompanyItemHolder->
            {
                holder.setData(getItemData(position))
            }
            is JoinPartnerHolder->
            {
                holder.setData(getItemData(position))
            }
            is JoinBangDangHolder->
            {
                holder.setData(getItemData(position))
            }
            is InviteShopHolder->
            {
                holder.setData(getItemData(position))
            }
            is JoinZhanHuiItemHolder->
            {
                holder.setData(getItemData(position))
            }
            is JoinIntroHolder->
            {
                holder.setData(getItemData(position))
            }
            is BannerViewHolder->
            {
                holder.setData(getItemData(position))
            }
            is JoinZhanHuiItemHolder->
            {
                holder.setData(getItemData(position))
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
