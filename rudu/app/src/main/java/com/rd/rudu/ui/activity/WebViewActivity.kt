package com.rd.rudu.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.app.utils.ViewUtils
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.databinding.ActivityWebviewBinding
import com.rd.rudu.ui.fragment.HomeWebFragment
import org.jetbrains.anko.startActivity

class WebViewActivity: BaseActivity<ActivityWebviewBinding>() {
    companion object
    {
        fun startActivity(context:Context,url:String?,showTitle:Boolean=true)
        {
            context.startActivity<WebViewActivity>(Pair(HomeWebFragment.WebUrl,url), Pair(Intent.ACTION_SHOW_APP_INFO,showTitle))
        }
    }
    override fun getLayoutResId(): Int
    {
        return R.layout.activity_webview
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val fragment=HomeWebFragment.newInstance(intent.getStringExtra(HomeWebFragment.WebUrl)?:getString(R.string.youzan_storeurl),intent.getBooleanExtra(Intent.ACTION_SHOW_APP_INFO,true))
        supportFragmentManager.beginTransaction().add(R.id.webView,fragment).commitAllowingStateLoss()
        var color=0xFF222222.toInt()
        toolbarBinding?.titleText?.setTextColor(color)
        toolbarBinding?.backBtn?.setImageDrawable(ViewUtils.setDrawableColor(this,color,R.mipmap.icon_back_b))
    }

    override fun backHandle()
    {
        super.onBackPressed()
    }
}