package com.rd.rudu.ui.activity

import android.graphics.Color
import android.os.Bundle
import com.google.android.app.utils.StatusBarUtil
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.databinding.ActivityJoincomanyBinding

class JoinCompanyActivity: BaseActivity<ActivityJoincomanyBinding>()
{
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
        toolbarBinding?.root?.setBackgroundColor(Color.TRANSPARENT)
        toolbarBinding?.backBtn?.setImageResource(R.mipmap.icon_back_b)
        toolbarBinding?.titleText?.setTextColor(Color.WHITE)
        setTitle("招商加盟")
//        SoftHideKeyBoardUtil.assistActivity(this)
    }
}