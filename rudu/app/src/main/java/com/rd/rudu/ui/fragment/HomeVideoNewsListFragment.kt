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
import com.rd.rudu.databinding.AdapterTopvideoitemBinding
import com.rd.rudu.databinding.FragmentHomeVideonewslistBinding

//头条
class HomeVideoNewsListFragment : BaseFragment<FragmentHomeVideonewslistBinding>() {
    val adapter:HomeVideoNewsListAdapter by lazy { HomeVideoNewsListAdapter(requireActivity()) }
    override fun getLayoutResId(): Int
    {
        return R.layout.fragment_home_videonewslist
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentBinding.recyclerView.adapter=adapter
        adapter.add("1")
        adapter.add("1")
        adapter.add("1")
        adapter.notifyDataSetChanged()
    }
}

 class HomeVideoNewsListAdapter(context: Context) : BaseRecyclerAdapter<String>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VideoListItemStyleHolder(context)
    }

    class VideoListItemStyleHolder(context: Context,layoutId: Int=R.layout.adapter_topvideoitem) :
        BaseViewHolder<AdapterTopvideoitemBinding>(context, layoutId)
}