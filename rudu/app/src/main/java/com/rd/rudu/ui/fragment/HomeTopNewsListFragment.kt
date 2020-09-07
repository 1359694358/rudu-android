package com.rd.rudu.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.app.adapter.BaseRecyclerAdapter
import com.google.android.app.adapter.BaseViewHolder
import com.google.android.app.widget.BaseFragment
import com.rd.rudu.R
import com.rd.rudu.databinding.AdapterTopnewsitem1Binding
import com.rd.rudu.databinding.AdapterTopnewsitem2Binding
import com.rd.rudu.databinding.FragmentHomeTopnewslistBinding
import com.rd.rudu.ui.adapter.NewsListItemDecoration

//头条
class HomeTopNewsListFragment : BaseFragment<FragmentHomeTopnewslistBinding>()
{
    val adapter:HomeTopNewsListAdapter by lazy { HomeTopNewsListAdapter(requireActivity()) }
    override fun getLayoutResId(): Int
    {
        return R.layout.fragment_home_topnewslist
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        contentBinding.recyclerView.adapter=adapter
        contentBinding.recyclerView.addItemDecoration(NewsListItemDecoration())
        adapter.add("1")
        adapter.add("1")
        adapter.add("1")
        adapter.notifyDataSetChanged()
    }
}

 class HomeTopNewsListAdapter(context: Context) : BaseRecyclerAdapter<String>(context)
 {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return NewsListItemStyle1Holder(context)
    }

    class NewsListItemStyle1Holder(context: Context,layoutId: Int=R.layout.adapter_topnewsitem1) :
        BaseViewHolder<AdapterTopnewsitem1Binding>(context, layoutId,WidthMatchParent)
    {

    }

     class NewsListItemStyle2Holder(context: Context,layoutId: Int=R.layout.adapter_topnewsitem2) :
         BaseViewHolder<AdapterTopnewsitem2Binding>(context, layoutId,WidthMatchParent)
}