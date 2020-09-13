package com.rd.rudu.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.google.android.app.utils.StatusBarUtil
import com.google.android.app.utils.logd
import com.google.android.app.widget.BaseActivity
import com.google.android.app.widget.BaseFragment
import com.google.android.app.widget.OnKeyBackHandle
import com.rd.rudu.R
import com.rd.rudu.databinding.FragmentHomeyouzanBinding
import com.rd.rudu.ui.activity.LoginActivity
import com.rd.rudu.ui.activity.WebViewActivity
import com.rd.rudu.utils.clearLoginState
import com.rd.rudu.vm.UserViewModel
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.youzan.androidsdk.YouzanSDK
import com.youzan.androidsdk.YouzanToken
import com.youzan.androidsdk.event.AbsAuthEvent
import com.youzan.androidsdk.event.AbsChooserEvent
import com.youzan.androidsdk.event.AbsShareEvent
import com.youzan.androidsdk.model.goods.GoodsShareModel
import org.jetbrains.anko.support.v4.startActivity


//首页有赞
class HomeWebFragment: BaseFragment<FragmentHomeyouzanBinding>(), OnKeyBackHandle {
    companion object
    {
        const val WebUrl="WebUrl"
        fun newInstance(url:String,showTitle:Boolean=true,title:String=""):HomeWebFragment
        {
            val fragment=HomeWebFragment()
            var bunde= bundleOf(Pair(WebUrl,url),Pair(Intent.ACTION_SHOW_APP_INFO,showTitle),Pair(Intent.EXTRA_TITLE,title))
            fragment.arguments=bunde
            return fragment
        }
    }

    private var url:String?=null
    override fun getLayoutResId(): Int
    {
        return R.layout.fragment_homeyouzan
    }
    var showTitle=true
    val userViewMode: UserViewModel by lazy { getViewModelByApplication(UserViewModel::class.java) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url=requireArguments().getString(WebUrl,requireActivity().resources.getString(R.string.youzan_storeurl))
        showTitle=requireArguments().getBoolean(Intent.ACTION_SHOW_APP_INFO,true)
        addObserver()
    }

    private fun addObserver()
    {
        if(requireActivity() is BaseActivity<*>)
        {
            (requireActivity() as BaseActivity<*>).addFragmentKeyBackHandle(this)
        }
        if(requireActivity() is WebViewActivity)
        {
            contentBinding.titleBar.visibility=View.GONE
        }
        else
        {
            var title:String?=requireArguments().getString(Intent.EXTRA_TITLE,"")
            contentBinding.titleBar.text=title
            contentBinding.titleBar.viewTreeObserver.addOnDrawListener {
                if(contentBinding.titleBar.layoutParams!=null&&contentBinding.titleBar.layoutParams is ViewGroup.MarginLayoutParams)
                {
                    (contentBinding.titleBar.layoutParams as ViewGroup.MarginLayoutParams).topMargin= StatusBarUtil.getStatusBarHeight(requireActivity())
                    contentBinding.titleBar.requestLayout()
                }
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
                    if(showTitle&&requireActivity() is WebViewActivity)
                    {
                        requireActivity().title = it
                    }
                }
            }
        })
        if(TextUtils.isEmpty(url))
        {
            url=resources.getString(R.string.youzan_storeurl)
        }
        contentBinding.mView.loadUrl(url)
        userViewMode.youzanTokenObserver.observe(this, Observer {
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
                if(isDetached||activity==null)
                    return
                if(needLogin)
                {
                    clearLoginState()
                    startActivity<LoginActivity>()
                }
            }
        })

        contentBinding.mView.subscribe(object : AbsShareEvent() {
            override fun call(view: Context, data: GoodsShareModel?) {
                if(data==null||isDetached||activity==null)
                    return
                //调用系统默认的分享
                val content = data.desc + " " + data.link
                val sendIntent = Intent()
                sendIntent.setPackage("com.tencent.mm")
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, content)
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, data.title)
                sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
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

    override fun processKeyBack(): Boolean
    {
        if(contentBinding.mView.pageCanGoBack()||contentBinding.mView.canGoBack())
        {
            contentBinding.mView.goBack()
            return true
        }
        return false
    }

    override fun onDestroy()
    {
        super.onDestroy()
    }
}