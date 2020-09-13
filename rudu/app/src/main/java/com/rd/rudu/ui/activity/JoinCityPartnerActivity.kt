package com.rd.rudu.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.app.net.TransUtils
import com.google.android.app.utils.ToastUtil
import com.google.android.app.utils.Utility
import com.google.android.app.utils.ViewUtils
import com.google.android.app.utils.getSerializableExtras
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.bean.request.PartnerApplyEntity
import com.rd.rudu.bean.result.BaseResultBean
import com.rd.rudu.bean.result.JoinPartnerIntroResultBean
import com.rd.rudu.databinding.ActivityJoinpartnerBinding
import com.rd.rudu.net.AppApi
import com.rd.rudu.net.AppModuleConfig

class JoinCityPartnerActivity: BaseActivity<ActivityJoinpartnerBinding>() {
    override fun getLayoutResId(): Int
    {
        return R.layout.activity_joinpartner
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("城市合伙人")
        var color=0xFF222222.toInt()
        toolbarBinding?.titleText?.setTextColor(color)
        toolbarBinding?.backBtn?.setImageDrawable(ViewUtils.setDrawableColor(this,color,R.mipmap.icon_back_b))
        var data: JoinPartnerIntroResultBean.JoinPartnerIntro?=intent.getSerializableExtras(Intent.ACTION_ATTACH_DATA)
        data?.let {
            contentBinding.data=it
            title = it.title
        }
        contentBinding.shangjiaForm.submit.setOnClickListener {
            submitHandle()
        }

        contentBinding.joinIntro.visibility= if(AppModuleConfig.appShowModules.contains(AppModuleConfig.Modules.合伙人报名.toString())) View.VISIBLE else View.GONE
        contentBinding.shangjiaForm.root.visibility= if(AppModuleConfig.appShowModules.contains(AppModuleConfig.Modules.合伙人联系及合作.toString())) View.VISIBLE else View.GONE
    }

    fun submitHandle()
    {
        if(contentBinding.shangjiaForm.name.editableText.isEmpty())
        {
            ToastUtil.show(this,"姓名不能为空")
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
        val entity=PartnerApplyEntity(
                contentBinding.shangjiaForm.name.editableText.toString(),
                contentBinding.shangjiaForm.type.editableText.toString(),
                contentBinding.shangjiaForm.shopName.editableText.toString(),
                contentBinding.shangjiaForm.phone.editableText.toString()
        )
        contentBinding.shangjiaForm.submit.isClickable=false
        showLoadingDialog()
        AppApi.serverApi.savePartnerApply(entity)
                .compose(TransUtils.ioTransformer<BaseResultBean<String>>())
                .compose(TransUtils.schedulersTransformer())
                .subscribe(
                        {
                            if(isFinishing||isDestroyed)
                                return@subscribe
                            contentBinding.shangjiaForm.submit.isClickable=true
                            hideLoadingDialog()
                            if(it?.yes()==true)
                            {
                                ToastUtil.show(this,"报名成功")
                                finish()
                            }
                            else
                            {
                                ToastUtil.show(this,"报名失败，请稍候再试")
                            }
                        }
                        ,
                        {
                            if(isFinishing||isDestroyed)
                                return@subscribe
                            contentBinding.shangjiaForm.submit.isClickable=true
                            hideLoadingDialog()
                            ToastUtil.show(this,"报名失败，请稍候再试")
                        }
                )
    }
}