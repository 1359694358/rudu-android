package com.rd.rudu.net

import com.google.android.app.net.JSONObjectConvertFactory
import com.google.android.app.net.SSLSocketClient
import com.google.android.app.utils.ExceptionHandler
import com.google.android.app.utils.logw
import com.google.android.app.utils.readAny
import com.rd.rudu.BuildConfig
import com.rd.rudu.bean.request.*
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.*
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager


interface ServerApi
{
    @POST("/api/auth/sendSms")
    fun getSmsCode(@Body phoneNumber: SmsCodeEntity):Observable<JSONObject>

    @POST("/api/auth/login")
    fun login(@Body loginEntity: LoginEntity):Observable<JSONObject>

    @Multipart
    @POST("/api/user/uploadAvatar")
    fun uploadAvatar(@Part file:MultipartBody.Part ):Observable<JSONObject>
    /*

    @POST("api/ap_user/yzLogin")
    fun sysYouZanUser(@Body youZanSysEntity: YouZanSysEntity):Observable<JSONObject>

    @POST("api/ap_user/alipayAuth")
    fun alipayAuth(@Body authEntoty: AlipayAuthEntoty):Observable<JSONObject>

    @POST("api/ap_user/bindPhone")
    fun socialLoginBind(@Body loginEntity: LoginEntity):Observable<JSONObject>*/

    @GET("/api/ap_user/getAppVersion")
    fun checkUpdate():Observable<JSONObject>

    //加盟banner
    @GET("/api/customer/getJoinSwipers")
    fun getJoinBanner():Observable<JSONObject>

    //招商加盟信息
    @GET("/api/customer/getJoinMerchants")
    fun getJoinMerchants():Observable<JSONObject>

    @GET("/api/customer/getJoinPartnerIntro")
    fun getJoinPartnerIntro():Observable<JSONObject>

    //品牌招商入驻
    @GET("/api/customer/getJoinBrandInfo")
    fun getJoinBrandInfo():Observable<JSONObject>

    //yudu简介
    @GET("/api/customer/getJoinIntroInfo")
    fun getJoinIntroInfo():Observable<JSONObject>

    @GET("/api/customer/getJoinExhibition")
    fun getJoinExhibition():Observable<JSONObject>

    @GET("/api/customer/getJoinBlast")
    fun getJoinBlast():Observable<JSONObject>

    @GET("/api/customer/getJoinMerchantsIntro")
    fun getJoinMerchantsIntro():Observable<JSONObject>

    @GET("/api/customer/getJoinMerchantsContact")
    fun getJoinMerchantsContact():Observable<JSONObject>

    @POST("api/customer/saveMerchantsApply")
    fun saveMerchantsApply(@Body data: MerchantsApplyEntity):Observable<JSONObject>

    @POST("/api/customer/savePartnerApply")
    fun savePartnerApply(@Body data:PartnerApplyEntity):Observable<JSONObject>

    @POST("/api/customer/saveBrandApply")
    fun saveBrandApply(@Body data: BrandApplyEntity):Observable<JSONObject>

    @GET("/api/customer/getJoinIntroElite")
    fun getJoinIntroElite():Observable<JSONObject>
}

object AppApi
{
    var ReadTimeOut = 5L
    var ConnectTimeOut = 5L
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
            logw("token:$token")
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

    fun buildFile(file:File):MultipartBody.Part
    {
        val requestBody: RequestBody =  file.asRequestBody("image/jpeg".toMediaType())
        val part: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, requestBody)
        return part
    }

}