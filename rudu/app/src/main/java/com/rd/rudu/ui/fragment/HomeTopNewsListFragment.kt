package com.rd.rudu.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.app.adapter.BaseRecyclerAdapter
import com.google.android.app.adapter.BaseViewHolder
import com.google.android.app.widget.BaseFragment
import com.rd.rudu.R
import com.rd.rudu.bean.result.NewsInfoListResultBean
import com.rd.rudu.databinding.AdapterTopnewsitem1Binding
import com.rd.rudu.databinding.AdapterTopnewsitem2Binding
import com.rd.rudu.databinding.FragmentHomeTopnewslistBinding
import com.rd.rudu.ui.activity.WebViewActivity
import com.rd.rudu.ui.adapter.NewsListItemDecoration
import com.rd.rudu.vm.NewsListVM

//头条
class HomeTopNewsListFragment : BaseFragment<FragmentHomeTopnewslistBinding>()
{
    val adapter:HomeTopNewsListAdapter by lazy { HomeTopNewsListAdapter(requireActivity()) }
    val newsListVm:NewsListVM by lazy { getViewModel<NewsListVM>() }
    override fun getLayoutResId(): Int
    {
        return R.layout.fragment_home_topnewslist
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        contentBinding.recyclerView.addItemDecoration(NewsListItemDecoration())
        contentBinding.recyclerView.adapter=adapter
        showLoading()
        newsListVm.newsListObs.observe(this, Observer {
            hideLoading()
            it?.data?.let {
                adapter.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
        newsListVm.loadNewsList()
    }

}

class HomeTopNewsListAdapter(context: Context) : BaseRecyclerAdapter<NewsInfoListResultBean.NewsInfoItem>(context)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        if(viewType>1)
        {
            return NewsListItemStyle2Holder(context)
        }
        return NewsListItemStyle1Holder(context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        when(holder)
        {
            is NewsListItemStyle1Holder->
            {
                holder.contentViewBinding.data=getItem(position)
            }
            is NewsListItemStyle2Holder->
            {
                holder.contentViewBinding.data=getItem(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int
    {
        return getItem(position).showPicCount
    }

    class NewsListItemStyle1Holder(context: Context,layoutId: Int=R.layout.adapter_topnewsitem1) :
            BaseViewHolder<AdapterTopnewsitem1Binding>(context, layoutId,WidthMatchParent)
    {

        init {
            itemView.setOnClickListener {
                WebViewActivity.startActivity(it.context,it.context.getString(R.string.newsurl,contentViewBinding.data!!.id),false)
            }
        }
    }

    class NewsListItemStyle2Holder(context: Context,layoutId: Int=R.layout.adapter_topnewsitem2) :
            BaseViewHolder<AdapterTopnewsitem2Binding>(context, layoutId,WidthMatchParent)
    {
        init {
            itemView.setOnClickListener {
                WebViewActivity.startActivity(it.context,it.context.getString(R.string.newsurl,contentViewBinding.data!!.id),false)
            }
        }
    }
}