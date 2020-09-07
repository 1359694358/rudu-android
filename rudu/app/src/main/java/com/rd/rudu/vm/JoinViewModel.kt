package com.rd.rudu.vm

import androidx.lifecycle.ViewModel
import com.google.android.app.net.MutableLiveDataX
import com.google.android.app.net.TransUtils
import com.google.android.app.utils.logw
import com.rd.rudu.bean.result.*
import com.rd.rudu.net.AppApi
import com.rd.rudu.ui.adapter.HomeJoinItemType
import com.rd.rudu.ui.adapter.*
import io.reactivex.Observable
//首页加盟列表数据
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
        var joinExhibitionResult=AppApi.serverApi.getJoinExhibition().compose(TransUtils.ioTransformer<JoinExhibitionResultBean>())
        var joinBlastResult=AppApi.serverApi.getJoinBlast().compose(TransUtils.ioTransformer<JoinBlastResultBean>())
        var joinGoodsResult=AppApi.serverApi.getJoinGGoods().compose(TransUtils.ioTransformer<JoinGoodsResultBean>())
        var joinFreshResult=AppApi.serverApi.getJoinFresh().compose(TransUtils.ioTransformer<JoinFreshResultBean>())
        var observableList= arrayOf(banner,
                merchants,
                chengshiHehuoren,
                brandBean,
                ruduIntro,
                joinExhibitionResult,
                joinBlastResult,
                joinGoodsResult,
                joinFreshResult)


        Observable.zipArray({responseList->
            var resultList= arrayListOf<HomeJoinItemType>()
            responseList.forEachIndexed { _, item ->
                logw("item:$item")
                if(item!=null&&item is BaseResultBean<*>)
                {
                    if(item.yes()&&item is HomeJoinItemType&&item.data!=null)
                    {
                        resultList.add(item)
                        if(item is JoinExhibitionResultBean)
                        {
                            resultList.addAll(item.data)
                        }
                        else if(item is JoinGoodsResultBean)
                        {
                            resultList.addAll(item.data)
                        }
                        else if(item is JoinFreshResultBean)
                        {
                            resultList.addAll(item.data)
                        }
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