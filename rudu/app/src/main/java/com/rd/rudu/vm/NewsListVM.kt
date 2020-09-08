package com.rd.rudu.vm

import androidx.lifecycle.ViewModel
import com.google.android.app.net.TransUtils
import com.google.android.app.utils.liveDataOf
import com.rd.rudu.bean.result.NewsInfoListResultBean
import com.rd.rudu.net.AppApi

class NewsListVM: ViewModel()
{
    val newsListObs=liveDataOf<NewsInfoListResultBean?>()

    fun loadNewsList()
    {
        AppApi.serverApi.getNewsInfo().compose(TransUtils.jsonTransform<NewsInfoListResultBean>())
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
}