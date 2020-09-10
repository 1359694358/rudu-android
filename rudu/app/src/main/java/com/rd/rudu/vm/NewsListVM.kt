package com.rd.rudu.vm

import androidx.lifecycle.ViewModel
import com.google.android.app.net.TransUtils
import com.google.android.app.utils.liveDataOf
import com.rd.rudu.bean.request.NewsListRequestBean
import com.rd.rudu.bean.result.NewsInfoListResultBean
import com.rd.rudu.bean.result.VideoInfoListResultBean
import com.rd.rudu.net.AppApi

class NewsListVM: ViewModel()
{
    val newsListObs=liveDataOf<NewsInfoListResultBean?>()

    val videoListObs=liveDataOf<VideoInfoListResultBean?>()

    fun loadNewsList(pageIndex:Int)
    {
        AppApi.serverApi.getNewsInfo(NewsListRequestBean(pageIndex)).compose(TransUtils.jsonTransform<NewsInfoListResultBean>())
                .compose(TransUtils.schedulersTransformer())
                .subscribe(
                        {
                            newsListObs.postValue(it)
                        }
                        ,
                        {
                            newsListObs.postValue(null)
                        }
                )
    }

    fun loadVideoList(pageIndex:Int)
    {
        AppApi.serverApi.getNewsVideo(NewsListRequestBean(pageIndex)).compose(TransUtils.jsonTransform<VideoInfoListResultBean>())
                .compose(TransUtils.schedulersTransformer())
                .subscribe(
                        {
                            videoListObs.postValue(it)
                        }
                        ,
                        {
                            videoListObs.postValue(null)
                        }
                )
    }
}