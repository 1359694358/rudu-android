package com.rd.rudu.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.google.android.app.utils.StatusBarUtil
import com.google.android.app.utils.logd
import com.google.android.app.widget.BaseFragment
import com.rd.rudu.R
import com.rd.rudu.databinding.FragmentHomeyouzanBinding
import com.rd.rudu.ui.activity.LoginActivity
import com.rd.rudu.utils.clearLoginState
import com.rd.rudu.vm.LoginViewModel
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.youzan.androidsdk.YouzanSDK
import com.youzan.androidsdk.YouzanToken
import com.youzan.androidsdk.event.AbsAuthEvent
import com.youzan.androidsdk.event.AbsChooserEvent
import org.jetbrains.anko.support.v4.startActivity

//首页有赞
class HomeWebFragment: BaseFragment<FragmentHomeyouzanBinding>()
{
    companion object
    {
        const val WebUrl="WebUrl"
        fun newInstance(url:String):HomeWebFragment
        {
            val fragment=HomeWebFragment()
            var bunde= bundleOf(Pair(WebUrl,url))
            fragment.arguments=bunde
            return fragment
        }
    }

    private var url:String?=null
    override fun getLayoutResId(): Int
    {
        return R.layout.fragment_homeyouzan
    }
    val loginViewMode: LoginViewModel by lazy { getViewModelByApplication(LoginViewModel::class.java) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url=requireArguments().getString(WebUrl,requireActivity().resources.getString(R.string.youzan_storeurl))
        addObserver()
    }

    private fun addObserver()
    {
        contentBinding.titleBar.viewTreeObserver.addOnDrawListener {
            if(contentBinding.titleBar.layoutParams!=null&&contentBinding.titleBar.layoutParams is ViewGroup.MarginLayoutParams)
            {
                (contentBinding.titleBar.layoutParams as ViewGroup.MarginLayoutParams).topMargin= StatusBarUtil.getStatusBarHeight(requireActivity())
                contentBinding.titleBar.requestLayout()
            }
        }
        contentBinding.mView.setWebChromeClient(object : WebChromeClient() {
            override fun onShowCustomView(
                view: View?,
                customViewCallback: IX5WebChromeClient.CustomViewCallback
            ) {
                super.onShowCustomView(view, customViewCallback)
                customViewCallback.onCustomViewHidden()
            }

            override fun onReceivedTitle(p0: WebView?, p1: String?) {
                super.onReceivedTitle(p0, p1)
                p1?.let {
//                    setTitle(it)
                }
            }
        })
        /* contentBinding.mView.setWebViewClient(object: WebViewClient()
         {
             override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                 contentBinding.mView.loadUrl(url)
                 return true;
             }
         })*/
        if(TextUtils.isEmpty(url))
        {
            url=resources.getString(R.string.youzan_storeurl)
        }
        contentBinding.mView.loadUrl(url)
        loginViewMode.youzanTokenObserver.observe(this, Observer {
            if(it?.yes()==true)
            {
                logd("登录成功 获取到有赞token")
                YouzanSDK.userLogout(requireContext());
                //调用login接口, 获取数据, 组装成YouzanToken, 回传给SDK
                var token =  YouzanToken()
                token.cookieKey = it.data.cookieKey
                token.cookieValue = it.data.cookieValue
                // 这里注意调用顺序。先传递给sdk，再刷新view
                YouzanSDK.sync(requireContext(), token);
                contentBinding.mView.sync(token)
            }
            else
            {
                logd("获取到有赞token失败")
                contentBinding.mView.loadUrl(url)
            }
        })
        contentBinding.mView.subscribe(object : AbsChooserEvent() {
            override fun call(p0: Context?, p1: Intent?, p2: Int) {
                startActivityForResult(p1,p2)
            }
        })
        contentBinding.mView.subscribe(object : AbsAuthEvent()
        {
            override fun call(context: Context, needLogin:Boolean )
            {
                if(needLogin)
                {
                        clearLoginState()
                        startActivity<LoginActivity>()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK)
        {
            contentBinding.mView.receiveFile(requestCode,data)
        }
    }
}