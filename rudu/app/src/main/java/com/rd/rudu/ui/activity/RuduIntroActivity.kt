package com.rd.rudu.ui.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.app.utils.ViewUtils
import com.google.android.app.utils.getSerializableExtras
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.bean.result.JoinIntroInfoResultBean
import com.rd.rudu.databinding.ActivityRuduintroBinding

/**
 * 玉都简介
 */
class RuduIntroActivity : BaseActivity<ActivityRuduintroBinding>() {
    override fun getLayoutResId(): Int
    {
        return R.layout.activity_ruduintro
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("誉都简介")
        var color=0xFF222222.toInt()
        toolbarBinding?.titleText?.setTextColor(color)
        toolbarBinding?.backBtn?.setImageDrawable(ViewUtils.setDrawableColor(this,color,R.mipmap.icon_back_b))
        var introData: JoinIntroInfoResultBean.JoinIntroInfoListData?=intent.getSerializableExtras(
            Intent.ACTION_ATTACH_DATA)
        introData?.let {
            contentBinding.intro=it
        }
    }
}