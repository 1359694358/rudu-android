package com.rd.rudu.ui.activity

import android.os.Bundle
import com.google.android.app.utils.ViewUtils
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.databinding.ActivityJoinbrandBinding

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
        setTitle("城市合伙人")
        var color=0xFF222222.toInt()
        toolbarBinding?.titleText?.setTextColor(color)
        toolbarBinding?.backBtn?.setImageDrawable(ViewUtils.setDrawableColor(this,color,R.mipmap.icon_back_b))
    }

}