package com.rd.rudu.utils

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.android.app.utils.clearCache
import com.google.android.app.utils.saveAny
import com.rd.rudu.bean.result.LoginResultBean
import com.rd.rudu.net.AppApi
import com.youzan.androidsdk.YouzanSDK


fun saveAppToken(token:String?)
{
    saveAny(AppApi.Token,token)
}

fun Fragment.clearLoginState()
{
    requireActivity().clearLoginState()
}
fun Context.clearLoginState()
{
    LoginResultBean.LoginResult.setLoginResult(null)
    clearCache(AppApi.Token)
    YouzanSDK.userLogout(this)
}