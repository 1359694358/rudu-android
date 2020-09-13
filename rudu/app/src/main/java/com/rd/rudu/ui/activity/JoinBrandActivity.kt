package com.rd.rudu.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ImageSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.set
import com.google.android.app.net.TransUtils
import com.google.android.app.utils.ToastUtil
import com.google.android.app.utils.Utility
import com.google.android.app.utils.ViewUtils
import com.google.android.app.utils.getSerializableExtras
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.bean.request.BrandApplyEntity
import com.rd.rudu.bean.request.PartnerApplyEntity
import com.rd.rudu.bean.result.BaseResultBean
import com.rd.rudu.bean.result.JoinBrandInfoResultBean
import com.rd.rudu.databinding.ActivityJoinbrandBinding
import com.rd.rudu.net.AppApi
import com.rd.rudu.net.AppModuleConfig

//品牌招商入驻
class JoinBrandActivity: BaseActivity<ActivityJoinbrandBinding>()
{
    override fun getLayoutResId(): Int
    {
        return R.layout.activity_joinbrand
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setTitle("品牌招商入驻")
        var color=0xFF222222.toInt()
        toolbarBinding?.titleText?.setTextColor(color)
        toolbarBinding?.backBtn?.setImageDrawable(ViewUtils.setDrawableColor(this,color,R.mipmap.icon_back_b))

        var data: JoinBrandInfoResultBean.JoinBrandInfoItem?=intent.getSerializableExtras(Intent.ACTION_ATTACH_DATA)
        data?.let {
            contentBinding.data=it
            title=it.title
            var percent1Span=SpannableStringBuilder("*${it.bgl}%")
            percent1Span[0,1]=ImageSpan(this,R.mipmap.upgrade_percent)
            var percent2Span=SpannableStringBuilder("*${it.fkl}%")
            val drawable=ContextCompat.getDrawable(this,R.mipmap.upgrade_percent)
            drawable!!.setBounds(0,0,resources.getDimensionPixelOffset(R.dimen.dimen9),resources.getDimensionPixelOffset(R.dimen.dimen12))
            percent1Span[0,1]=ImageSpan(drawable!!,ImageSpan.ALIGN_BASELINE)
            percent2Span[0,1]=ImageSpan(drawable!!,ImageSpan.ALIGN_BASELINE)
            var sizeSpan=AbsoluteSizeSpan(resources.getDimensionPixelOffset(R.dimen.dimen14))
            percent1Span[percent1Span.length-1,percent1Span.length]=sizeSpan
            percent2Span[percent2Span.length-1,percent2Span.length]=sizeSpan
            contentBinding.percent1.text=percent1Span
            contentBinding.percent2.text=percent2Span
        }
        contentBinding.shangjiaForm.submit.setOnClickListener {
            submitHandle()
        }

        contentBinding.xiaoguoTop.visibility= if(AppModuleConfig.appShowModules.contains(AppModuleConfig.Modules.营销效果.toString())) View.VISIBLE else View.GONE
        contentBinding.xiaoguoBottom.visibility= if(AppModuleConfig.appShowModules.contains(AppModuleConfig.Modules.营销效果.toString())) View.VISIBLE else View.GONE
        contentBinding.pingtaiyoushi.visibility= if(AppModuleConfig.appShowModules.contains(AppModuleConfig.Modules.平台优势.toString())) View.VISIBLE else View.GONE
        contentBinding.shangjiaForm.root.visibility= if(AppModuleConfig.appShowModules.contains(AppModuleConfig.Modules.品牌联系及合作.toString())) View.VISIBLE else View.GONE
    }
    fun submitHandle()
    {
        if(contentBinding.shangjiaForm.shangjiadizhi.editableText.isEmpty())
        {
            ToastUtil.show(this,"商家地址不能为空")
            return
        }
        if(contentBinding.shangjiaForm.type.editableText.isEmpty())
        {
            ToastUtil.show(this,"商家类型不能为空")
            return
        }
        if(contentBinding.shangjiaForm.shopName.editableText.isEmpty())
        {
            ToastUtil.show(this,"商家类型不能为空")
            return
        }
        if(!Utility.isMobileNO(contentBinding.shangjiaForm.phone.editableText.toString()))
        {
            ToastUtil.show(this,"请输入正确的手机号")
            return
        }
        val entity= BrandApplyEntity(
                contentBinding.shangjiaForm.shangjiadizhi.editableText.toString(),
                contentBinding.shangjiaForm.type.editableText.toString(),
                contentBinding.shangjiaForm.shopName.editableText.toString(),
                contentBinding.shangjiaForm.phone.editableText.toString()
        )
        contentBinding.shangjiaForm.submit.isClickable=false
        showLoadingDialog()
        AppApi.serverApi.saveBrandApply(entity)
                .compose(TransUtils.ioTransformer<BaseResultBean<String>>())
                .compose(TransUtils.schedulersTransformer())
                .subscribe(
                        {
                            if(isFinishing||isDestroyed)
                                return@subscribe
                            hideLoadingDialog()
                            contentBinding.shangjiaForm.submit.isClickable=true
                            if(it?.yes()==true)
                            {
                                ToastUtil.show(this,"提交成功")
                                finish()
                            }
                            else
                            {
                                ToastUtil.show(this,"提交失败，请稍候再试")
                            }
                        }
                        ,
                        {
                            if(isFinishing||isDestroyed)
                                return@subscribe
                            contentBinding.shangjiaForm.submit.isClickable=true
                            hideLoadingDialog()
                            ToastUtil.show(this,"提交失败，请稍候再试")
                        }
                )
    }

}