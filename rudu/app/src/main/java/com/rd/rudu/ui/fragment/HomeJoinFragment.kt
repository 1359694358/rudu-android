package com.rd.rudu.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.google.android.app.utils.addVLayoutImpl
import com.google.android.app.widget.BaseFragment
import com.rd.rudu.R
import com.rd.rudu.databinding.FragmentHomejoinBinding
import com.rd.rudu.ui.adapter.BannerAdapter
import com.rd.rudu.ui.adapter.CompanyItemAdapter

class HomeJoinFragment: BaseFragment<FragmentHomejoinBinding>()
{
    override fun getLayoutResId(): Int
    {
        return R.layout.fragment_homejoin
    }

    val bannerAdapter:BannerAdapter by lazy { BannerAdapter(requireContext()) }
    val companyItemAdapter: CompanyItemAdapter by lazy { CompanyItemAdapter(requireContext()) }
    val adapter: DelegateAdapter by lazy { contentBinding.recyclerView.addVLayoutImpl() }
    val appAdapters = mutableListOf< DelegateAdapter.Adapter<out RecyclerView.ViewHolder>>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        bannerAdapter.add("1")
        companyItemAdapter.add("1")
        companyItemAdapter.add("1")
        appAdapters.add(bannerAdapter)
        appAdapters.add(companyItemAdapter)
        adapter.setAdapters(appAdapters)
    }
}