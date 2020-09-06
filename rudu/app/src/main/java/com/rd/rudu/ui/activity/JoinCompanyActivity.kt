package com.rd.rudu.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.app.utils.*
import com.google.android.app.utils.imageloader.ImageLoader
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.bean.request.MerchantsApplyEntity
import com.rd.rudu.bean.result.JoinMerChantsBean
import com.rd.rudu.databinding.ActivityJoincomanyBinding
import com.rd.rudu.databinding.LayoutZhaoshangIntroitemBinding
import com.rd.rudu.vm.JoinMerchantsVM

class JoinCompanyActivity: BaseActivity<ActivityJoincomanyBinding>()
{
    private val joinMerchantsVM: JoinMerchantsVM by lazy { getViewModel<JoinMerchantsVM>() }
    override fun getLayoutResId(): Int
    {
        return R.layout.activity_joincomany
    }

    override fun getFitSystemWindow(): Boolean
    {
        return false
    }

    override fun getStatusBarColor(): Int
    {
        return Color.TRANSPARENT
    }
    override fun setStatusBarMode()
    {
        StatusBarUtil.setDarkMode(this)
    }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        showLoading()
        toolbarBinding?.root?.setBackgroundColor(Color.TRANSPARENT)
        toolbarBinding?.backBtn?.setImageDrawable(ViewUtils.setDrawableColor(this,Color.WHITE,R.mipmap.icon_back_b))
        toolbarBinding?.titleText?.setTextColor(Color.WHITE)
        title = "招商加盟"
        joinMerchantsVM.joinMerchantsObs.observe(this, Observer{
            hideLoading()
            it.first.data.forEach {item->
                var introBinding=LayoutZhaoshangIntroitemBinding.inflate(layoutInflater,contentBinding.merchantsIntro,true)
                introBinding.introInfo=item
            }
            contentBinding.contractInfo= it.second.data
        })
        joinMerchantsVM.loadData()
        var data: JoinMerChantsBean.ChantsData?=intent.getSerializableExtras(Intent.ACTION_ATTACH_DATA)
        data?.let {
            ImageLoader.loader.load(contentBinding.bannerImage,it.topUrl)
        }

        contentBinding.submit.setOnClickListener {

            if(contentBinding.area.editableText.isEmpty())
            {
                ToastUtil.show(this,"加盟区域不能为空")
                return@setOnClickListener
            }
            if(contentBinding.joinName.editableText.isEmpty())
            {
                ToastUtil.show(this,"姓名不能为空")
                return@setOnClickListener
            }
            if(!Utility.isMobileNO(contentBinding.joinPhone.editableText.toString()))
            {
                ToastUtil.show(this,"请输入正确的手机号")
                return@setOnClickListener
            }
            var entity=MerchantsApplyEntity(contentBinding.joinName.editableText.toString(),
            if(contentBinding.female.isChecked)"1" else "0",contentBinding.area.editableText.toString(),contentBinding.joinPhone.editableText.toString())
            joinMerchantsVM.saveMerchantsApply(entity)
        }

        joinMerchantsVM.joinSubmitObs.observe(this, Observer {
            if(it?.yes()==true)
            {
                ToastUtil.show(this,"提交申请成功")
                finish()
            }
            else
            {
                ToastUtil.show(this,"提交申请失败，请稍候再试")
            }
        })
    }
}