package com.rd.rudu.vm

import androidx.lifecycle.ViewModel
import com.google.android.app.net.MutableLiveDataX
import com.google.android.app.net.TransUtils
import com.google.android.app.utils.loge
import com.google.android.app.utils.logw
import com.rd.rudu.bean.request.MerchantsApplyEntity
import com.rd.rudu.bean.result.BaseResultBean
import com.rd.rudu.bean.result.JoinMerchantsContactResultBean
import com.rd.rudu.bean.result.JoinMerchantsIntroResultBean
import com.rd.rudu.net.AppApi
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
//招商加盟页面
class JoinMerchantsVM: ViewModel()
{
    val joinMerchantsObs=MutableLiveDataX< Pair<JoinMerchantsIntroResultBean, JoinMerchantsContactResultBean>>()
    val joinSubmitObs=MutableLiveDataX<BaseResultBean<String>?>()

    fun loadData()
    {
        var joinMerchantsIntro=AppApi.serverApi.getJoinMerchantsIntro().compose(TransUtils.ioTransformer<JoinMerchantsIntroResultBean>())
        var joinMerchantsContact=AppApi.serverApi.getJoinMerchantsContact().compose(TransUtils.ioTransformer<JoinMerchantsContactResultBean>())
        Observable.zip(joinMerchantsIntro,joinMerchantsContact, BiFunction<JoinMerchantsIntroResultBean, JoinMerchantsContactResultBean, Pair<JoinMerchantsIntroResultBean, JoinMerchantsContactResultBean>> { t1, t2 ->
            return@BiFunction Pair(t1,t2)
        })
                .compose(TransUtils.schedulersTransformer())
                .subscribe(
                        {
                            joinMerchantsObs.postValue(it)
                        }
                        ,
                        {
//                            joinMerchantsObs.postValue(it)
                            loge("error ${it.message}")
                        }
                )
    }

    fun saveMerchantsApply(entity: MerchantsApplyEntity)
    {
        AppApi.serverApi.saveMerchantsApply(entity)
                .compose(TransUtils.ioTransformer<BaseResultBean<String>>())
                .subscribe(
                        {
                            joinSubmitObs.postValue(it)
                        }
                        ,
                        {
                            joinSubmitObs.postValue(null)
                        }
                )
    }
}