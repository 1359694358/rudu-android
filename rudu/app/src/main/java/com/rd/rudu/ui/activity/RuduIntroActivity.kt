package com.rd.rudu.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.app.adapter.BaseRecyclerAdapter
import com.google.android.app.adapter.BaseViewHolder
import com.google.android.app.utils.ToastUtil
import com.google.android.app.utils.ViewUtils
import com.google.android.app.utils.getSerializableExtras
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.bean.result.JoinIntroInfoResultBean
import com.rd.rudu.bean.result.JoinIntroMainsResultBean
import com.rd.rudu.databinding.ActivityRuduintroBinding
import com.rd.rudu.databinding.LayoutIntroProductItemBinding
import com.rd.rudu.databinding.LayoutTeamPartnerBinding
import com.rd.rudu.databinding.LayoutZhaoshangIntroitemBinding
import com.rd.rudu.net.AppModuleConfig
import com.rd.rudu.ui.adapter.GridStyleItemDecoration
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
            title=it.title
        }
        showLoading()
        loadData()

        contentBinding.aboutRudu.visibility=if(AppModuleConfig.appShowModules.contains(AppModuleConfig.Modules.关于誉都.toString())) View.VISIBLE else View.GONE
        contentBinding.teamContainer.visibility=if(AppModuleConfig.appShowModules.contains(AppModuleConfig.Modules.精英团队.toString())) View.VISIBLE else View.GONE
        contentBinding.gongsiyoushi.visibility=if(AppModuleConfig.appShowModules.contains(AppModuleConfig.Modules.公司优势.toString())) View.VISIBLE else View.GONE
        contentBinding.productLayout.root.visibility=if(AppModuleConfig.appShowModules.contains(AppModuleConfig.Modules.主打产品.toString())) View.VISIBLE else View.GONE
        contentBinding.contractUsContainer.visibility=if(AppModuleConfig.appShowModules.contains(AppModuleConfig.Modules.简介联系我们.toString())) View.VISIBLE else View.GONE
    }

    private fun loadData()
    {
        ruduOntroVm.introObs.observe(this, Observer {
            hideLoading()
            if(it!=null)
            {
                if(it.teamData.data?.isEmpty()==true)
                {
                    contentBinding.teamContainer.visibility=View.GONE
                }
                else
                {
                    it.teamData.data?.forEach {item->
                        var teamBinding=LayoutTeamPartnerBinding.inflate(layoutInflater,contentBinding.teamItems,true)
                        teamBinding.teamItem=item
                    }
                }
                if(it.youshi.data?.isEmpty()==true)
                {
                    contentBinding.gongsiyoushi.visibility=View.GONE
                }
                else
                {
                    it.youshi.data?.forEach {item->
                        var introBinding= LayoutZhaoshangIntroitemBinding.inflate(layoutInflater,contentBinding.merchantsIntro,true)
                        introBinding.introInfo=item
                    }
                }
                var adapter=ProductListAdapter(it.product.data,this)
                val lm=GridLayoutManager(this,2)
                lm.orientation=GridLayoutManager.VERTICAL
                contentBinding.productLayout.productList.addItemDecoration(GridStyleItemDecoration(resources.getDimensionPixelSize(R.dimen.dimen10)))
                contentBinding.productLayout.productList.layoutManager=lm
                contentBinding.productLayout.productList.adapter=adapter
            }
            else
            {
                ToastUtil.show(this,"获取数据失败")
            }
        })
        ruduOntroVm.loadData()
    }
}

class ProductListAdapter(data: MutableList<JoinIntroMainsResultBean.JoinIntroMainsItem>?, context: Context) : BaseRecyclerAdapter<JoinIntroMainsResultBean.JoinIntroMainsItem>(data, context)
{

    class ProductItemHolder(context: Context):BaseViewHolder<LayoutIntroProductItemBinding>(context,R.layout.layout_intro_product_item)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductItemHolder(context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if(holder is ProductItemHolder)
        {
            holder.contentViewBinding.product=getItem(position)
        }
    }

}