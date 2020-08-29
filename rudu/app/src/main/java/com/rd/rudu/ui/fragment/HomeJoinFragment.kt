package com.rd.rudu.ui.fragment

import android.os.Bundle
import android.view.View
import com.google.android.app.widget.BaseFragment
import com.rd.rudu.R
import com.rd.rudu.databinding.FragmentHomejoinBinding
import com.rd.rudu.ui.adapter.HomeJoinListAdapter
import com.rd.rudu.ui.adapter.buildJoinList

class HomeJoinFragment: BaseFragment<FragmentHomejoinBinding>()
{
    override fun getLayoutResId(): Int
    {
        return R.layout.fragment_homejoin
    }

    val adapter: HomeJoinListAdapter by lazy { HomeJoinListAdapter(requireActivity()) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        contentBinding.recyclerView.adapter=adapter
        adapter.addAll(buildJoinList())
        adapter.notifyDataSetChanged()
        contentBinding.refreshLayout.setOnLoadMoreListener {

        }
        contentBinding.refreshLayout.setOnRefreshListener {

        }
        contentBinding.refreshLayout.setEnableLoadMore(false)
    }
}