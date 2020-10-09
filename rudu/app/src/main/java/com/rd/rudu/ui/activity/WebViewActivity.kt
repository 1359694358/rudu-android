package com.rd.rudu.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.URLUtil
import com.google.android.app.utils.ViewUtils
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.databinding.ActivityWebviewBinding
import com.rd.rudu.ui.fragment.YouZanWebFragment
import org.jetbrains.anko.startActivity

class WebViewActivity: BaseActivity<ActivityWebviewBinding>() {
    companion object
    {
        fun startActivity(context:Context,url:String?,showTitle:Boolean=true,showMore:Boolean=true)
        {
            if(URLUtil.isNetworkUrl(url))
                context.startActivity<WebViewActivity>(Pair(YouZanWebFragment.WebUrl,url), Pair(Intent.ACTION_SHOW_APP_INFO,showTitle),Pair(Intent.ACTION_MEDIA_SHARED,showMore))
        }
    }
    override fun getLayoutResId(): Int
    {
        return R.layout.activity_webview
    }

    private var webFragment:YouZanWebFragment?=null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val fragment=YouZanWebFragment.newInstance(intent.getStringExtra(YouZanWebFragment.WebUrl)?:getString(R.string.youzan_storeurl),intent.getBooleanExtra(Intent.ACTION_SHOW_APP_INFO,true))
        webFragment=fragment
        supportFragmentManager.beginTransaction().replace(R.id.webView,fragment).commitAllowingStateLoss()
        var color=0xFF222222.toInt()
        toolbarBinding?.titleText?.setTextColor(color)
        var showMore=intent.getBooleanExtra(Intent.ACTION_MEDIA_SHARED,true)
        if(showMore)
        {
            toolbarBinding?.moreText?.setTextColor(color)
            toolbarBinding?.moreText?.paint?.isFakeBoldText=true
            setMoreText(" … ")
        }
        toolbarBinding?.backBtn?.setImageDrawable(ViewUtils.setDrawableColor(this,color,R.mipmap.icon_back_b))
    }

    override fun backHandle()
    {
        super.onBackPressed()
    }

    override fun moreClick() {
        super.moreClick()
        webFragment?.sharePage()
    }
}