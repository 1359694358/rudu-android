package com.rd.rudu.vm

import androidx.lifecycle.ViewModel
import com.google.android.app.net.MutableLiveDataX
import com.google.android.app.net.TransUtils
import com.rd.rudu.bean.result.JoinIntroEliteResultBean
import com.rd.rudu.bean.result.JoinIntroMainsResultBean
import com.rd.rudu.bean.result.JoinMerchantsIntroResultBean
import com.rd.rudu.net.AppApi
import io.reactivex.Observable
import io.reactivex.functions.Function3

data class RuduIntroDetail(var teamData:JoinIntroEliteResultBean,var youshi: JoinMerchantsIntroResultBean,var product: JoinIntroMainsResultBean)

class RuduIntroVM: ViewModel()
{
    val introObs=MutableLiveDataX<RuduIntroDetail?>()

    fun loadData()
    {
        var teamInfo=AppApi.serverApi.getJoinIntroElite().compose(TransUtils.ioTransformer<JoinIntroEliteResultBean>())
        var youshi=AppApi.serverApi.getJoinIntroAdvantage().compose(TransUtils.ioTransformer<JoinMerchantsIntroResultBean>())
        var product=AppApi.serverApi.getJoinIntroMains().compose(TransUtils.ioTransformer<JoinIntroMainsResultBean>())

        Observable.zip(teamInfo,youshi,product, Function3<JoinIntroEliteResultBean, JoinMerchantsIntroResultBean, JoinIntroMainsResultBean, RuduIntroDetail> { t1, t2, t3 ->
            var result=RuduIntroDetail(t1,t2,t3)
            return@Function3 result
        })
                .compose(TransUtils.schedulersTransformer())
                .subscribe(
                        {
                            introObs.postValue(it)
                        }
                        ,
                        {
                            introObs.postValue(null)
                        }
                )
    }
}