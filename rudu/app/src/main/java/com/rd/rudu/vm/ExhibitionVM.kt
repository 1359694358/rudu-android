package com.rd.rudu.vm

import androidx.lifecycle.ViewModel
import com.google.android.app.net.MutableLiveDataX
import com.google.android.app.net.TransUtils
import com.google.android.app.net.TransUtils.schedulersTransformer
import com.rd.rudu.bean.result.ExhibitionSpecialResultBean
import com.rd.rudu.bean.result.ExhibitionStarResultBean
import com.rd.rudu.bean.result.JoinExhibitionNewResultBean
import com.rd.rudu.net.AppApi
import io.reactivex.Observable
import io.reactivex.functions.Function3

data class ExhibitionInfo(var exhibitionNew:JoinExhibitionNewResultBean, var specialExhibition: ExhibitionSpecialResultBean, var starExhibition: ExhibitionStarResultBean)
class ExhibitionVM: ViewModel()
{
    val exhibitionObs=MutableLiveDataX<ExhibitionInfo?>()
    fun loadExhibitionInfo(id:String)
    {
        var exhibitionNew=AppApi.serverApi.getJoinExhibitionNew(id).compose(TransUtils.ioTransformer<JoinExhibitionNewResultBean>())
        var specialExhibition= AppApi.serverApi.getJoinExhibitionSpecial(id).compose(TransUtils.ioTransformer<ExhibitionSpecialResultBean>())
        var starExhibition= AppApi.serverApi.getJoinExhibitionStar(id).compose(TransUtils.ioTransformer<ExhibitionStarResultBean>())

        Observable.zip(exhibitionNew,specialExhibition,starExhibition,Function3<JoinExhibitionNewResultBean, ExhibitionSpecialResultBean, ExhibitionStarResultBean,ExhibitionInfo>{ t1, t2, t3->
            val result=ExhibitionInfo(t1,t2,t3)
            return@Function3 result
        })
                .compose(schedulersTransformer())
                .subscribe(
                        {
                            exhibitionObs.postValue(it)
                        }
                        ,
                        {
                            exhibitionObs.postValue(null)
                        }
                )

    }
}