package com.rd.rudu.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.app.net.MutableLiveDataX
import com.google.android.app.net.TransUtils
import com.google.android.app.utils.logw
import com.rd.rudu.bean.request.LoginEntity
import com.rd.rudu.bean.request.LoginType
import com.rd.rudu.bean.request.SmsCodeEntity
import com.rd.rudu.bean.request.YouZanSysEntity
import com.rd.rudu.bean.result.BaseResultBean
import com.rd.rudu.bean.result.LoginResultBean
import com.rd.rudu.bean.result.SmsCodeBean
import com.rd.rudu.bean.result.YouZanTokenBean
import com.rd.rudu.net.AppApi
import io.reactivex.Observable
import org.json.JSONObject
import java.io.File

class UserViewModel: ViewModel()
{

    val smsCodeObserver= MutableLiveDataX<SmsCodeBean?>()
    val loginObserver=MutableLiveDataX<LoginResultBean?>()
    val youzanTokenObserver=MutableLiveDataX<YouZanTokenBean?>()
    fun getSmsCode(phone: String)
    {
        var smsCodeEntity= SmsCodeEntity(phone)
        AppApi.serverApi.getSmsCode(smsCodeEntity).compose(TransUtils.jsonTransform(SmsCodeBean::class.java))
            .compose(TransUtils.schedulersTransformer())
            .subscribe(
                {
                    smsCodeObserver.postValue(it)
                }
                ,
                {
                    smsCodeObserver.postValue(null)
                }
            )
    }
    //telephone	否	string	用户手机号
//wx_id	否	string	微信id
//zfb_id	否	string	支付宝id
//channel_type	是	number	登录方式 0：手机； 1：微信；2：支付宝
//verKey	否	string	验证码标识，手机登录时
//verCode	否	string	验证码，手机登录时
    fun login(youzanClientId:String,nickName:String?,avatar:String?,channel_type:Int= LoginType.Mobile, telephone:String="", verKey:String="", verCode:String="", wx_id:String="", zfb_id:String=""):LoginEntity
    {
        var loginEntity=LoginEntity(channel_type, telephone, verKey, verCode, wx_id, zfb_id,nickName,avatar)
        AppApi.serverApi.login(loginEntity).compose(TransUtils.schedulersTransformer())
            .compose(TransUtils.jsonTransform(LoginResultBean::class.java))
            .subscribe(
                {

                    if(channel_type!=LoginType.Mobile&&it.code==40001)
                    {
                        loginObserver.postValue(it)
                        return@subscribe
                    }
                    if(it.yes()&&it.data?.user?.yzLoginResponse?.yes()==true)
                    {
                        loginObserver.postValue(it)
                        if(it.data?.user?.yzLoginResponse?.data!=null)
                            youzanTokenObserver.postValue(it.data?.user?.yzLoginResponse)
                    }
                    else
                    {
                        LoginResultBean.LoginResult.setLoginResult(null)
                        loginObserver.postValue(null)
                        youzanTokenObserver.postValue(null)
                    }
                }
                ,
                {
                    LoginResultBean.LoginResult.setLoginResult(null)
                    loginObserver.postValue(null)
                    youzanTokenObserver.postValue(null)
                }
            )
        return loginEntity
        /*.compose(TransUtils.jsonTransform(LoginResultBean::class.java))
        .flatMap {
            var observable: Observable<JSONObject>?=null
            if(it.yes())
            {
                it.data.avatar=avatar
                it.data.nickName=nickName
                it.data.telephone=telephone
                val youzEntity= YouZanSysEntity(it.data.id,youzanClientId,avatar,nickName,telephone)
                observable= AppApi.serverApi.sysYouZanUser(youzEntity)
            }
            loginObserver.postValue(it)
            return@flatMap observable
        }
        .compose(TransUtils.schedulersTransformer())
        .compose(TransUtils.jsonTransform(YouZanTokenBean::class.java))
        .subscribe(
                {
                    youzanTokenObserver.postValue(it)
                }
                ,
                {
                    LoginResultBean.LoginResult.setLoginResult(null)
                    youzanTokenObserver.postValue(null)
                }
        )*/
    }

    /*   fun getYouZanToken(userId:String,youzanClientId:String,nickName:String?,avatar:String?,telephone:String?)
       {
           val youzEntity= YouZanSysEntity(userId,youzanClientId,avatar,nickName)
           AppApi.serverApi.sysYouZanUser(youzEntity).compose(TransUtils.jsonTransform(YouZanTokenBean::class.java))
                   .compose(TransUtils.schedulersTransformer())
                   .subscribe(
                           {
                               youzanTokenObserver.postValue(it)
                           }
                           ,
                           {
                               youzanTokenObserver.postValue(null)
                           }
                   )
       }*/

    /*
    fun socialLoginBind(loginEntity: LoginEntity)
    {
        AppApi.serverApi.socialLoginBind(loginEntity).compose(TransUtils.schedulersTransformer())
            .compose(TransUtils.jsonTransform(LoginResultBean::class.java))
            .subscribe(
                {
                    if(it.code==40002)//绑定了已经绑定过的手机号
                    {
                        loginObserver.postValue(it)
                        return@subscribe
                    }
                    else if(it.yes()&&it.data?.yzLoginResponse?.yes()==true&&it.data?.yzLoginResponse?.data!=null)
                    {
                        loginObserver.postValue(it)
                        youzanTokenObserver.postValue(it.data?.yzLoginResponse)
                    }
                    else
                    {
                        LoginResultBean.LoginResult.setLoginResult(null)
                        loginObserver.postValue(null)
                        youzanTokenObserver.postValue(null)
                    }
                }
                ,
                {
                    LoginResultBean.LoginResult.setLoginResult(null)
                    loginObserver.postValue(null)
                    youzanTokenObserver.postValue(null)
                }
            )
    }*/

    fun changeImage(file:File)
    {
        AppApi.serverApi.uploadAvatar(AppApi.buildFile(file)).compose(TransUtils.schedulersTransformer())
                .subscribe(
                        {
                            logw("$it")
                        }
                ,
                        {
                            logw("$it")
                        }
                )
    }
}