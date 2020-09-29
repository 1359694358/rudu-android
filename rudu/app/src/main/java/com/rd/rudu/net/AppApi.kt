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
import okhttp3.RequestBody.Companion.toRequestBody
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

    @GET("/api/user/getUserInfo")
    fun getUserInfo(@Query("id")id:String)

    @POST("/api/user/updateUserById")
    fun updateUserById(@Body updateUserInfo: UpdateUserInfo)

    @Multipart
    @POST("/api/user/uploadAvatar")
    fun uploadAvatar(@Part("user_id")user_id:RequestBody, @Part file:MultipartBody.Part ):Observable<JSONObject>
    /*

    @POST("api/ap_user/yzLogin")
    fun sysYouZanUser(@Body youZanSysEntity: YouZanSysEntity):Observable<JSONObject>

    @POST("api/ap_user/alipayAuth")
    fun alipayAuth(@Body authEntoty: AlipayAuthEntoty):Observable<JSONObject>

    @POST("api/ap_user/bindPhone")
    fun socialLoginBind(@Body loginEntity: LoginEntity):Observable<JSONObject>*/

    @GET("/api/auth/getAppVersion")
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

    //我们的团队人员
    @GET("/api/customer/getJoinIntroElite")
    fun getJoinIntroElite():Observable<JSONObject>

    //介绍
    @GET("/api/customer/getJoinIntroAdvantage")
    fun getJoinIntroAdvantage():Observable<JSONObject>

    //主打产品
    @GET("/api/customer/getJoinIntroMains")
    fun getJoinIntroMains():Observable<JSONObject>
    //好货推荐
    @GET("/api/customer/getJoinGGoods")
    fun getJoinGGoods():Observable<JSONObject>

    //新品偿鲜
    @GET("/api/customer/getJoinFresh")
    fun getJoinFresh():Observable<JSONObject>

    //加盟-展会新品发布
    @GET("/api/customer/getJoinExhibitionNew")
    fun getJoinExhibitionNew(@Query("exId")exId:String):Observable<JSONObject>

    //加盟-展会展品专区
    @GET("/api/customer/getJoinExhibitionSpecial")
    fun getJoinExhibitionSpecial(@Query("exId")exId:String):Observable<JSONObject>

    //加盟-展会明星单品
    @GET("/api/customer/getJoinExhibitionStar")
    fun getJoinExhibitionStar(@Query("exId")exId:String):Observable<JSONObject>

    //资讯列表
    @POST("/api/customer/getNewsInfo")
    fun getNewsInfo(@Body param:NewsListRequestBean):Observable<JSONObject>

    //视频列表
    @POST("/api/customer/getNewsVideo")
    fun getNewsVideo(@Body param:NewsListRequestBean):Observable<JSONObject>

    //功能列表
    @GET("/api/customer/getShowMoudles")
    fun getShowMoudles():Observable<JSONObject>
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

    fun convertToRequestBody(param: String): RequestBody {
        return param.toRequestBody("application/json; charset=utf-8".toMediaType())
    }
}