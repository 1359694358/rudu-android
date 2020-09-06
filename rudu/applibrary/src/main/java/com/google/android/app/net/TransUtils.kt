package com.google.android.app.net;


import android.util.Log
import com.google.gson.Gson
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject


object TransUtils {
    val gson=Gson()
    @JvmStatic
    fun <T> jsonTransform(classRef: Class<T>): ObservableTransformer<JSONObject, T> {
        return ObservableTransformer { upstream ->
            upstream.map { s ->
                Log.w("TransUtils","$s")
                gson.fromJson("$s",classRef)
            }
        }
    }

    inline fun <reified T> jsonTransform(): ObservableTransformer<JSONObject, T> {
        return ObservableTransformer { upstream ->
            upstream.map { s ->
                Log.w("TransUtils","$s")
                gson.fromJson("$s",T::class.java)
            }
        }
    }

    @JvmStatic
    fun  appLoginTokenExpireHandle(): ObservableTransformer<JSONObject, JSONObject> {
        return ObservableTransformer { upstream ->
            upstream.map { result ->
                result
            }
        }
    }

    fun <T> schedulersFlowableTransformer(): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    @JvmStatic
    fun <T> schedulersTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    //处理了多个并发 和 单个出错情况的返回
    @JvmStatic
    inline fun <reified T> ioTransformer(): ObservableTransformer<JSONObject, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io()).map { s ->
                Log.w("TransUtils","$s")
                gson.fromJson("$s",T::class.java)
            }.onErrorReturnItem(T::class.java.newInstance())
        }
    }



    fun schedulersCompletableTransformer(): CompletableTransformer {
        return CompletableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}
