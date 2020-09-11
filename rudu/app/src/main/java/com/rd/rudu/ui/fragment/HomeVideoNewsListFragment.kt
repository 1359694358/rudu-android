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
import com.rd.rudu.bean.result.VideoInfoListResultBean
import com.rd.rudu.databinding.AdapterTopvideoitemBinding
import com.rd.rudu.databinding.FragmentHomeVideonewslistBinding
import com.rd.rudu.ui.activity.TestVideoActivity.Companion.startVideoActivity
import com.rd.rudu.ui.adapter.NewsListItemDecoration
import com.rd.rudu.vm.NewsListVM
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

//头条
class HomeVideoNewsListFragment : BaseFragment<FragmentHomeVideonewslistBinding>(), OnRefreshLoadMoreListener {
    val adapter:HomeVideoNewsListAdapter by lazy { HomeVideoNewsListAdapter(requireActivity()) }
    val newsListVm: NewsListVM by lazy { getViewModel<NewsListVM>() }
    private var pageIndex = 1
    override fun getLayoutResId(): Int
    {
        return R.layout.fragment_home_videonewslist
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentBinding.recyclerView.addItemDecoration(NewsListItemDecoration())
        contentBinding.recyclerView.adapter=adapter
        showLoading()
        newsListVm.videoListObs.observe(this, Observer {
            hideLoading()
            contentBinding.refreshLayout.finishRefresh()
            contentBinding.refreshLayout.finishLoadMore()
            contentBinding.refreshLayout.setEnableLoadMore(it?.haveMore()?:false)
            it?.data?.let {data->
                if(pageIndex==1)
                {
                    adapter.data.clear()
                }
                adapter.addAll(data)
                adapter.notifyDataSetChanged()
            }
        })
        contentBinding.refreshLayout.setOnRefreshLoadMoreListener(this)
        onRefresh(contentBinding.refreshLayout)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout)
    {
        pageIndex+=1
        newsListVm.loadVideoList(pageIndex)
    }

    override fun onRefresh(refreshLayout: RefreshLayout)
    {
        pageIndex=1
        newsListVm.loadVideoList(pageIndex)
    }
}

class HomeVideoNewsListAdapter(context: Context) : BaseRecyclerAdapter<VideoInfoListResultBean.NewsInfoItem>(context)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return VideoListItemStyleHolder(context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if(holder is VideoListItemStyleHolder)
        {
            holder.contentViewBinding.videoInfo=getItem(position)
        }
    }

    class VideoListItemStyleHolder(context: Context,layoutId: Int=R.layout.adapter_topvideoitem) :
            BaseViewHolder<AdapterTopvideoitemBinding>(context, layoutId, WidthMatchParent)
    {
        init {
            contentViewBinding.playIcon.setOnClickListener {
                it.context.startVideoActivity(contentViewBinding.videoInfo!!.title!!,contentViewBinding.videoInfo!!.videoUrl!!)
            }
        }
    }
}