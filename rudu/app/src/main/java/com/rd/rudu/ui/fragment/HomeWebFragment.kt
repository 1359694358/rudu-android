package com.rd.rudu.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.app.utils.StatusBarUtil
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class HomeWebFragment: YouZanWebFragment()
{
    val bgColor=0xffdf0000.toInt()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentBinding.titleBar.setBackgroundColor(bgColor)
        contentBinding.titleBar.setTextColor(Color.WHITE)

        StatusBarUtil.setColor(requireActivity(),bgColor ,0)
        StatusBarUtil.setDarkMode(requireActivity())
    }

    override fun addWebClient() {
        super.addWebClient()
        contentBinding.mView.setWebViewClient(object: WebViewClient()
        {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.w("WebUrl",""+url)
                return super.shouldOverrideUrlLoading(view,url);
            }
        })
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden)
        {
            StatusBarUtil.setColor(requireActivity(),bgColor ,0)
            StatusBarUtil.setDarkMode(requireActivity())
        }
        else
        {
            StatusBarUtil.setColor(requireActivity(),Color.TRANSPARENT ,0)
            StatusBarUtil.setLightMode(requireActivity())
        }
    }
}