package com.rd.rudu

import android.util.Log
import com.google.android.app.DefaultApp
import com.google.android.app.utils.*
import com.mediacloud.app.share.SocialShareControl
import com.pgyersdk.crash.PgyCrashManager
import com.rd.rudu.R
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.youzan.androidsdk.YouzanSDK
import com.youzan.androidsdkx5.YouZanSDKX5Adapter
import com.youzan.androidsdkx5.YouzanPreloader
import org.jetbrains.anko.doAsync


class App: DefaultApp() {

    override fun onCreate() {
        super.onCreate()
        YouzanSDK.init(this@App, resources.getString(R.string.youzan_clientId),YouZanSDKX5Adapter())
        YouzanPreloader.preloadHtml(this@App, resources.getString(R.string.youzan_storeurl));
        doAsync {
            SocialShareControl.initAppKey(this@App)
            ExceptionHandler.getInstance().setCrashCallBack {
                PgyCrashManager.reportCaughtException(it as java.lang.Exception?)
            }
            //
            var use_terms=this@App.resources.getString(R.string.use_terms)
            var cacheDir=this@App.cacheDir.absolutePath
            Log.d("AppCache","cacheDir:$cacheDir")
            AssetsManager.copyAssetFile2SDCard(this@App,use_terms,FileUtil.createFile("${cacheDir}${use_terms}"))
            var app_policy=this@App.resources.getString(R.string.app_policy)
            AssetsManager.copyAssetFile2SDCard(this@App,app_policy,FileUtil.createFile("${cacheDir}${app_policy}"))

            val map = HashMap<String, Any>()
            map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
            map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
            try {
                QbSdk.initTbsSettings(map)
            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }

             val cb: QbSdk.PreInitCallback = object : QbSdk.PreInitCallback {
                override fun onViewInitFinished(arg0: Boolean) {
                    // TODO Auto-generated method stub
                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                    Log.d("app", " onViewInitFinished is $arg0")
                }

                override fun onCoreInitFinished() {
                    Log.d("app", " onCoreInitFinished")
                }
            }
            try {
                QbSdk.initX5Environment(this@App,cb)
            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }
        }
    }
}