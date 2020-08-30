package com.google.android.app

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication
import com.google.android.app.utils.*
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager
import com.tencent.mmkv.MMKV
import org.jetbrains.anko.doAsync


open class DefaultApp: MultiDexApplication(), ViewModelStoreOwner {
    private val mAppViewModelStore=ViewModelStore()

    private var mFactory: ViewModelProvider.Factory? = null

    override fun onCreate() {
        super.onCreate()
        QMUISwipeBackActivityManager.init(this)
        doAsync {
            ExceptionHandler.getInstance().init(this@DefaultApp)
            MMKV.initialize(this@DefaultApp)
        }
    }
    companion object
    {
        @JvmStatic
        fun initFile(context: Context)
        {
            doAsync {
                FileUtil.initPackage(context)
            }
        }
    }

    fun getAppViewModelProvider(activity: Activity): ViewModelProvider
    {
        return ViewModelProvider(this,(activity.applicationContext as DefaultApp).getAppFactory(activity))
    }

    private fun getAppFactory(activity: Activity): ViewModelProvider.Factory {
        val application = checkApplication(activity)
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        }
        return mFactory!!
    }

    private fun checkApplication(activity: Activity): Application {
        return activity.application
                ?: throw IllegalStateException("Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call.")
    }

    private fun checkActivity(fragment: Fragment): Activity? {
        return fragment.activity
                ?: throw IllegalStateException("Can't create ViewModelProvider for detached fragment")
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }
}