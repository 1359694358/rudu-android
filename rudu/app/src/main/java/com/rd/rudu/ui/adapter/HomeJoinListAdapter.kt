package com.rd.rudu.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.app.adapter.BaseRecyclerAdapter
import com.google.android.app.adapter.BaseRecyclerAdapter.LayoutSpanCount
import com.google.android.app.adapter.BaseRecyclerAdapter.OnlyOneSpan
import com.google.android.app.adapter.BaseViewHolder
import com.google.android.app.widget.LoopPager
import com.rd.rudu.R
import com.rd.rudu.databinding.AdapterJoinBannerBinding
import com.rd.rudu.databinding.AdapterJoinBannerItemBinding
import com.rd.rudu.databinding.AdapterJoinCompanyBinding

interface HomeJoinItemType
{
    fun getJointItemType():Pair<Int,Int>
}

class HomeJoinItem(var joinItemType: Pair<Int,Int>):HomeJoinItemType
{
    override fun getJointItemType(): Pair<Int, Int> {
        return joinItemType
    }

}

val JOINT_TYPE_BANNER=Pair(1,LayoutSpanCount)
val JOINT_TYPE_IMAGE1=Pair(2,LayoutSpanCount)
val JOINT_TYPE_IMAGE2=Pair(3,LayoutSpanCount)
val JOINT_TYPE_INTRO=Pair(4,LayoutSpanCount)
val JOINT_TYPE_ZHANHUI_HEADER=Pair(5,LayoutSpanCount)
val JOINT_TYPE_ZHANHUI_ITEM=Pair(6,OnlyOneSpan)
val JOINT_TYPE_BANGDANG=Pair(7,OnlyOneSpan)
val JOINT_TYPE_HaoHuoTuiJianHeader=Pair(8,OnlyOneSpan)
val JOINT_TYPE_HaoHuoTuiJianItem=Pair(9,OnlyOneSpan)
val JOINT_TYPE_XinXianChangHeader=Pair(10,OnlyOneSpan)
val JOINT_TYPE_XinXianChangItem=Pair(11,OnlyOneSpan)

fun buildJoinList():List<HomeJoinItemType>
{
    var list= mutableListOf<HomeJoinItemType>()
    list.add(HomeJoinItem(JOINT_TYPE_BANNER))
    list.add(HomeJoinItem(JOINT_TYPE_IMAGE1))
    /*  list.add(HomeJoinItem(JOINT_TYPE_IMAGE2))
      list.add(HomeJoinItem(JOINT_TYPE_INTRO))
      list.add(HomeJoinItem(JOINT_TYPE_ZHANHUI_HEADER))
      list.add(HomeJoinItem(JOINT_TYPE_ZHANHUI_ITEM))
      list.add(HomeJoinItem(JOINT_TYPE_BANGDANG))
      list.add(HomeJoinItem(JOINT_TYPE_HaoHuoTuiJianHeader))
      list.add(HomeJoinItem(JOINT_TYPE_HaoHuoTuiJianItem))
      list.add(HomeJoinItem(JOINT_TYPE_XinXianChangHeader))
      list.add(HomeJoinItem(JOINT_TYPE_XinXianChangHeader))
      list.add(HomeJoinItem(JOINT_TYPE_XinXianChangItem))
      list.add(HomeJoinItem(JOINT_TYPE_XinXianChangItem))
      list.add(HomeJoinItem(JOINT_TYPE_XinXianChangItem))*/
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


class HomeJoinListAdapter(context: Context) : BaseRecyclerAdapter<HomeJoinItemType>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType)
        {
            JOINT_TYPE_BANNER.first->
                BannerViewHolder(R.layout.adapter_join_banner,context)
            JOINT_TYPE_IMAGE1.first->
                CompanyItemHolder(R.layout.adapter_join_company,context)
            else->
                object:RecyclerView.ViewHolder(View(context)){}
        }
        /*  if(viewType== JOINT_TYPE_IMAGE1.first)
              return CompanyItemHolder(R.layout.adapter_join_company,context)*/
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if(holder is BannerViewHolder)
        {
            holder.setData()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getJointItemType().first
    }

    override fun getItemSpanCount(position: Int): Int {
        return getItem(position).getJointItemType().second
    }
}
