package com.rd.rudu.vm

import androidx.lifecycle.ViewModel
import com.google.android.app.net.MutableLiveDataX
import com.google.android.app.net.TransUtils
import com.google.android.app.utils.logw
import com.rd.rudu.bean.result.*
import com.rd.rudu.net.AppApi
import com.rd.rudu.ui.adapter.HomeJoinItemType
import io.reactivex.Observable

class JoinViewModel: ViewModel()
{
    val joinObserver=MutableLiveDataX<List<HomeJoinItemType>?>()


    fun loadJoinNavListData()
    {
        //列表顺序 从上到下
        var banner=AppApi.serverApi.getJoinBanner().compose(TransUtils.ioTransformer<JoinBannerResultBean>())
        var merchants=AppApi.serverApi.getJoinMerchants().compose(TransUtils.ioTransformer<JoinMerChantsBean>())
        var chengshiHehuoren=AppApi.serverApi.getJoinPartnerIntro().compose(TransUtils.ioTransformer<JoinPartnerIntroResultBean>())
        var brandBean=AppApi.serverApi.getJoinBrandInfo().compose(TransUtils.ioTransformer<JoinBrandInfoResultBean>())
        var ruduIntro=AppApi.serverApi.getJoinIntroInfo().compose(TransUtils.ioTransformer<JoinIntroInfoResultBean>())

        var observableList= arrayOf(banner,merchants,chengshiHehuoren,brandBean,ruduIntro)


        Observable.zipArray({responseList->
            var resultList= arrayListOf<HomeJoinItemType>()
            responseList.forEachIndexed { _, item ->
                logw("item:$item")
                if(item!=null&&item is BaseResultBean<*>)
                {
                    if(item.yes()&&item is HomeJoinItemType&&item.data!=null)
                    {
                        resultList.add(item)
                    }
                }
            }
            return@zipArray resultList
        },false,observableList.size,observableList)
            .compose(TransUtils.schedulersTransformer())
            .subscribe(
                {
                    logw("$it")
                    joinObserver.postValue(it)
                }
                ,
                {
                    logw("$it")
                    joinObserver.postValue(null)
                }
            )
    }
    fun buildJoinListData(data:List<HomeJoinItemType>):List<HomeJoinItemType>
    {
        return data
    }
}