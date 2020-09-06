package com.rd.rudu.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.app.utils.StatusBarUtil
import com.google.android.app.widget.BaseFragment
import com.rd.rudu.R
import com.rd.rudu.databinding.FragmentHomejoinBinding
import com.rd.rudu.ui.adapter.HomeJoinItemType
import com.rd.rudu.ui.adapter.HomeJoinListAdapter
import com.rd.rudu.ui.adapter.buildJoinList
import com.rd.rudu.vm.JoinViewModel

class HomeJoinFragment(var jointViewModel: JoinViewModel): BaseFragment<FragmentHomejoinBinding>()
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
            jointViewModel.loadJoinNavListData()
        }
        contentBinding.refreshLayout.setEnableLoadMore(false)
        contentBinding.titleBar.viewTreeObserver.addOnDrawListener {
            if(contentBinding.titleBar.layoutParams!=null&&contentBinding.titleBar.layoutParams is ViewGroup.MarginLayoutParams)
            {
                (contentBinding.titleBar.layoutParams as ViewGroup.MarginLayoutParams).topMargin=StatusBarUtil.getStatusBarHeight(requireActivity())
                contentBinding.titleBar.requestLayout()
            }
        }
        jointViewModel.joinObserver.observeSticky(this, Observer {
            contentBinding.refreshLayout.finishLoadMore()
            contentBinding.refreshLayout.finishRefresh()
            if(it!=null)
            {
                adapter.data.clear()
                adapter.data.addAll(jointViewModel.buildJoinListData(it))
                adapter.notifyDataSetChanged()
            }
        })
    }

}