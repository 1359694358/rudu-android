package com.rd.rudu.ui.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.app.utils.ViewUtils
import com.google.android.app.utils.getSerializableExtras
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.bean.result.JoinExhibitionResultBean
import com.rd.rudu.databinding.ActivityExhibitioninfoBinding

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
            setTitle(it.title)
        }
        showLoading()
    }

}