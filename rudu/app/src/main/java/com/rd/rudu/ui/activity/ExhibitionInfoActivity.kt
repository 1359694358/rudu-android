package com.rd.rudu.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.app.adapter.BaseRecyclerAdapter
import com.google.android.app.adapter.BaseViewHolder
import com.google.android.app.utils.ViewUtils
import com.google.android.app.utils.getSerializableExtras
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.bean.result.ExhibitionSpecialResultBean
import com.rd.rudu.bean.result.JoinExhibitionResultBean
import com.rd.rudu.databinding.ActivityExhibitioninfoBinding
import com.rd.rudu.databinding.LayoutExhibitionGooditemBinding
import com.rd.rudu.databinding.LayoutExhibitionstaritemLeftimageBinding
import com.rd.rudu.databinding.LayoutExhibitionstaritemRightimageBinding
import com.rd.rudu.vm.ExhibitionVM

//展会 信息
class ExhibitionInfoActivity: BaseActivity<ActivityExhibitioninfoBinding>()
{

    override fun getLayoutResId(): Int
    {
        return R.layout.activity_exhibitioninfo
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setTitle("展会信息")
        var color=0xFF222222.toInt()
        toolbarBinding?.titleText?.setTextColor(color)
        toolbarBinding?.backBtn?.setImageDrawable(ViewUtils.setDrawableColor(this,color,R.mipmap.icon_back_b))
        var data: JoinExhibitionResultBean.JoinExhibitionResult?=intent.getSerializableExtras(Intent.ACTION_ATTACH_DATA)
        data?.let {
            contentBinding.exhibition=it
            title = it.title
            loadData(it)
        }
        showLoading()
    }

    private val exhibitionVM:ExhibitionVM by lazy { getViewModel<ExhibitionVM>() }

    private fun loadData(exhibitionData: JoinExhibitionResultBean.JoinExhibitionResult)
    {
        exhibitionVM.exhibitionObs.observe(this, Observer {
            hideLoading()
            if(it!=null)
            {
                if(it.exhibitionNew.data!=null)
                    contentBinding.detail=it.exhibitionNew.data
                contentBinding.specialProductList.adapter=SpecialProductListAdapter(it.specialExhibition.data,this)

                it.starExhibition.data?.forEachIndexed { idx, item ->
                    var index=(idx+1)
                    if(idx%2==0)
                    {
                        val starBindingLeft=LayoutExhibitionstaritemLeftimageBinding.inflate(layoutInflater,contentBinding.starProductLayout.starList,true)
                        starBindingLeft.number.text=if(index<10)"0${index}" else "$index"
                        starBindingLeft.item=item
                    }
                    else
                    {
                        val starBindingRight=LayoutExhibitionstaritemRightimageBinding.inflate(layoutInflater,contentBinding.starProductLayout.starList,true)
                        starBindingRight.number.text=if(index<10)"0${index}" else "$index"
                        starBindingRight.item=item
                    }
                }
            }
        })
        exhibitionVM.loadExhibitionInfo(exhibitionData.id)
    }


    class SpecialProductListAdapter(data: MutableList<ExhibitionSpecialResultBean.ExhibitionSpecialProduct>?, context: Context) : BaseRecyclerAdapter<ExhibitionSpecialResultBean.ExhibitionSpecialProduct>(data, context)
    {
        class SpecialProductItemHolder(context: Context,layoutType: Int = None) : BaseViewHolder<LayoutExhibitionGooditemBinding>(context, R.layout.layout_exhibition_gooditem, layoutType)
        {
            init {
                contentViewBinding.zhanhuijiaju.viewTreeObserver.addOnGlobalLayoutListener {
                    val location = IntArray(2)
                    var lp=contentViewBinding.zhanhuijiaju.layoutParams
                    if(lp is ViewGroup.MarginLayoutParams)
                    {
                        contentViewBinding.zhanhuijiaju.getLocationOnScreen(location)
                        if(location[0]<(itemView.resources.displayMetrics.widthPixels/3))
                        {
                            lp.leftMargin=0
                            lp.rightMargin=context.resources.getDimensionPixelOffset(R.dimen.dimen4)
                        }
                        else
                        {
                            lp.rightMargin=0
                            lp.leftMargin=context.resources.getDimensionPixelOffset(R.dimen.dimen4)
                        }
                        contentViewBinding.zhanhuijiaju.requestLayout()
                    }
                }
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return SpecialProductItemHolder(context)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            super.onBindViewHolder(holder, position)
            if(holder is SpecialProductItemHolder)
            {
                holder.contentViewBinding.goods=getItem(position)
            }
        }

    }
}