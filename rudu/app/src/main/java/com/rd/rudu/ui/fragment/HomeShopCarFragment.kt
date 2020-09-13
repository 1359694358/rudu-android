package com.rd.rudu.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.rd.rudu.R
import com.rd.rudu.ui.activity.HomeActivity
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class HomeShopCarFragment: YouZanWebFragment()
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun addWebClient() {
        super.addWebClient()
        contentBinding.mView.setWebViewClient(object: WebViewClient()
        {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.w("WebUrl",""+url)
                if(requireActivity().getString(R.string.quguanguan_redirect)==url)
                {
                    if(requireActivity() is HomeActivity)
                    {
                        (requireActivity() as HomeActivity).checkedTableIndex(0)
                        return true
                    }
                }
                return super.shouldOverrideUrlLoading(view,url);
            }

            override fun shouldInterceptRequest(p0: WebView, p1: WebResourceRequest): WebResourceResponse {
                return super.shouldInterceptRequest(p0, p1)
            }
        })
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden)
        {
            if(TextUtils.isEmpty(url))
            {
                url=resources.getString(R.string.youzan_storeurl)
            }
            contentBinding.mView.loadUrl(url)
        }
    }
}