package com.rd.rudu.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.app.utils.ToastUtil
import com.google.android.app.utils.ViewUtils
import com.google.android.app.utils.getSerializableExtras
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.bean.result.JoinIntroInfoResultBean
import com.rd.rudu.databinding.ActivityRuduintroBinding
import com.rd.rudu.databinding.LayoutTeamPartnerBinding
import com.rd.rudu.databinding.LayoutZhaoshangIntroitemBinding
import com.rd.rudu.vm.RuduIntroVM

/**
 * 玉都简介
 */
class RuduIntroActivity : BaseActivity<ActivityRuduintroBinding>() {
    override fun getLayoutResId(): Int
    {
        return R.layout.activity_ruduintro
    }

    val ruduOntroVm: RuduIntroVM by lazy { getViewModel<RuduIntroVM>() }

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
        showLoading()
        loadData()
    }

    private fun loadData()
    {
        ruduOntroVm.introObs.observe(this, Observer {
            hideLoading()
            if(it!=null)
            {
                it.teamData.data?.forEach {item->
                    var teamBinding=LayoutTeamPartnerBinding.inflate(layoutInflater,contentBinding.teamItems,true)
                    teamBinding.teamItem=item
                }
                it.youshi.data?.forEach {item->
                    var introBinding= LayoutZhaoshangIntroitemBinding.inflate(layoutInflater,contentBinding.merchantsIntro,true)
                    introBinding.introInfo=item
                }
            }
            else
            {
                ToastUtil.show(this,"获取数据失败")
            }
        })
        ruduOntroVm.loadData()
    }
}