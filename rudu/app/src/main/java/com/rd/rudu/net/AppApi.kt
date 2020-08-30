package com.rd.rudu.net

import com.google.android.app.net.JSONObjectConvertFactory
import com.google.android.app.net.SSLSocketClient
import com.google.android.app.utils.ExceptionHandler
import com.google.android.app.utils.readAny
import com.rd.rudu.BuildConfig
import com.rd.rudu.bean.request.LoginEntity
import com.rd.rudu.bean.request.SmsCodeEntity
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager
import io.reactivex.Observable
import okhttp3.Request
import org.json.JSONObject
import retrofit2.http.*

interface ServerApi
{
    @POST("/api/auth/sendSms")
    fun getSmsCode(@Body phoneNumber: SmsCodeEntity):Observable<JSONObject>

    @POST("/api/auth/login")
    fun login(@Body loginEntity: LoginEntity):Observable<JSONObject>

    @POST("/api/customer/uploadAvatar")
    fun uploadAvatar()
    /*

    @POST("api/ap_user/yzLogin")
    fun sysYouZanUser(@Body youZanSysEntity: YouZanSysEntity):Observable<JSONObject>

    @POST("api/ap_user/alipayAuth")
    fun alipayAuth(@Body authEntoty: AlipayAuthEntoty):Observable<JSONObject>

    @POST("api/ap_user/bindPhone")
    fun socialLoginBind(@Body loginEntity: LoginEntity):Observable<JSONObject>*/

    @GET("api/ap_user/getAppVersion")
    fun checkUpdate():Observable<JSONObject>
}

object AppApi
{
    var ReadTimeOut = 5L
    var ConnectTimeOut = 5L
    //服务器接口地址修改就改这
//    val Host="https://m.runtae.com"
//    val Host="http://www.huimiao.wang"
    const val Token="Authorization"
    val serverApi:ServerApi
    init {
        RxJavaPlugins.setErrorHandler { throwable ->
            ExceptionHandler.getInstance().saveCrashInfo2File(throwable)
        }
        val okhttpBuilder = OkHttpClient.Builder()
        okhttpBuilder.addInterceptor {
            val original: Request = it.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .addHeader("Content-Type", "application/json")
            var token:String?= readAny(Token)
            if(token?.isNotEmpty()==true)
                requestBuilder.addHeader(Token,token)
            val request: Request = requestBuilder.build()
            return@addInterceptor it.proceed(request)
        }
        okhttpBuilder.readTimeout(ReadTimeOut, TimeUnit.SECONDS)
        okhttpBuilder.connectTimeout(ConnectTimeOut, TimeUnit.SECONDS)
        okhttpBuilder.sslSocketFactory(
            SSLSocketClient.getSSLSocketFactory(),
            SSLSocketClient.getTrustManager()[0] as X509TrustManager
        )
        okhttpBuilder.hostnameVerifier(SSLSocketClient.getHostnameVerifier())
        val okHttpClient = okhttpBuilder.build()
        val retrofit= Retrofit.Builder()
            .baseUrl(BuildConfig.Host)
            .addConverterFactory(JSONObjectConvertFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        serverApi=retrofit.create(ServerApi::class.java)
    }

}