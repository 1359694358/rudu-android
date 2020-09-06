package com.rd.rudu.ui.activity

import android.content.Context
import android.os.Bundle
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.databinding.ActivityWebviewBinding
import com.rd.rudu.ui.fragment.HomeWebFragment
import org.jetbrains.anko.startActivity

class WebViewActivity: BaseActivity<ActivityWebviewBinding>() {
    companion object
    {
        fun startActivity(context:Context,url:String?)
        {
            context.startActivity<WebViewActivity>(Pair(HomeWebFragment.WebUrl,url))
        }
    }
    override fun getLayoutResId(): Int
    {
        return R.layout.activity_webview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragment=HomeWebFragment.newInstance(intent.getStringExtra(HomeWebFragment.WebUrl)?:getString(R.string.youzan_storeurl))
        supportFragmentManager.beginTransaction().add(R.id.webView,fragment).commitAllowingStateLoss()
    }
}