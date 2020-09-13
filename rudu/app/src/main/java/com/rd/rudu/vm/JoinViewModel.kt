package com.rd.rudu.vm

import androidx.lifecycle.ViewModel
import com.google.android.app.net.MutableLiveDataX
import com.google.android.app.net.TransUtils
import com.google.android.app.utils.logw
import com.rd.rudu.bean.result.*
import com.rd.rudu.net.AppApi
import com.rd.rudu.net.AppModuleConfig
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

        AppApi.serverApi.getShowMoudles().compose(TransUtils.jsonTransform<BaseResultBean<String>>())
                .flatMap {
                    var modules= it.data.split(",")
                    AppModuleConfig.appShowModules.clear()
                    AppModuleConfig.appShowModules.addAll(modules)
                    var banner=AppApi.serverApi.getJoinBanner().compose(TransUtils.ioTransformer<JoinBannerResultBean>())
                    var observableArr= mutableListOf<Observable<out BaseResultBean<*>>>(banner)

                    if(modules.contains(AppModuleConfig.Modules.招商加盟.toString()))
                    {
                        var merchants=AppApi.serverApi.getJoinMerchants().compose(TransUtils.ioTransformer<JoinMerChantsBean>())
                        observableArr.add(merchants)
                    }
                    if(modules.contains(AppModuleConfig.Modules.城市合伙人加盟.toString()))
                    {
                        var chengshiHehuoren=AppApi.serverApi.getJoinPartnerIntro().compose(TransUtils.ioTransformer<JoinPartnerIntroResultBean>())
                        observableArr.add(chengshiHehuoren)
                    }

                    if(modules.contains(AppModuleConfig.Modules.品牌招商入驻.toString()))
                    {
                        var brandBean=AppApi.serverApi.getJoinBrandInfo().compose(TransUtils.ioTransformer<JoinBrandInfoResultBean>())
                        observableArr.add(brandBean)
                    }

                    if(modules.contains(AppModuleConfig.Modules.誉都简介.toString()))
                    {
                        var ruduIntro=AppApi.serverApi.getJoinIntroInfo().compose(TransUtils.ioTransformer<JoinIntroInfoResultBean>())
                        observableArr.add(ruduIntro)
                    }
                    if(modules.contains(AppModuleConfig.Modules.展会信息.toString()))
                    {
                        var joinExhibitionResult=AppApi.serverApi.getJoinExhibition().compose(TransUtils.ioTransformer<JoinExhibitionResultBean>())
                        observableArr.add(joinExhibitionResult)
                    }

                    if(modules.contains(AppModuleConfig.Modules.爆款榜单.toString()))
                    {
                        var joinBlastResult=AppApi.serverApi.getJoinBlast().compose(TransUtils.ioTransformer<JoinBlastResultBean>())
                        observableArr.add(joinBlastResult)
                    }

                    if(modules.contains(AppModuleConfig.Modules.好货推荐.toString()))
                    {
                        var joinGoodsResult=AppApi.serverApi.getJoinGGoods().compose(TransUtils.ioTransformer<JoinGoodsResultBean>())
                        observableArr.add(joinGoodsResult)
                    }

                    if(modules.contains(AppModuleConfig.Modules.新品尝鲜.toString()))
                    {
                        var joinFreshResult=AppApi.serverApi.getJoinFresh().compose(TransUtils.ioTransformer<JoinFreshResultBean>())
                        observableArr.add(joinFreshResult)
                    }
                    var array=observableArr.toTypedArray()
                    return@flatMap  Observable.zipArray({responseList->
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
                    },false,array.size,array)
                }.compose(TransUtils.schedulersTransformer())
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


       /* Observable.zipArray({responseList->
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
            )*/
    }
    fun buildJoinListData(data:List<HomeJoinItemType>):List<HomeJoinItemType>
    {
        return data
    }
}