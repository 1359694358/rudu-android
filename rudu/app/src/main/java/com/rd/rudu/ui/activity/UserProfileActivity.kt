package com.rd.rudu.ui.activity

import android.os.Bundle
import com.google.android.app.utils.ViewUtils
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.bean.result.LoginResultBean
import com.rd.rudu.databinding.ActivityPersonalInfoBinding
import com.rd.rudu.vm.UserViewModel

class UserProfileActivity: BaseActivity<ActivityPersonalInfoBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_personal_info
    }

    val userViewModel:UserViewModel by lazy { getViewModelByApplication<UserViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        title = "个人信息"
        setMoreText("保存")
        var color=0xFF222222.toInt()
        toolbarBinding?.titleText?.setTextColor(color)
        toolbarBinding?.moreText?.setTextColor(color)
        toolbarBinding?.backBtn?.setImageDrawable(ViewUtils.setDrawableColor(this,color,R.mipmap.icon_back_b))
        var loginResult= LoginResultBean.LoginResult.getLoginResult()

    }
}