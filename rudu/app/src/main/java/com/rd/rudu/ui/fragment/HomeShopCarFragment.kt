package com.rd.rudu.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.android.app.utils.ViewUtils
import com.rd.rudu.R
import com.rd.rudu.ui.activity.HomeActivity
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class HomeShopCarFragment: YouZanWebFragment()
{
    lateinit var backDrawable: Drawable
    private val color=0xFF222222.toInt()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backDrawable=ViewUtils.setDrawableColor(requireActivity(),color, R.mipmap.icon_back_b)
        contentBinding.backBtn?.setImageDrawable(backDrawable)
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
        })
    }
    override fun setFragmentTitle(title: CharSequence) {
        if(!flag)
        {
            flag=true
            return
        }
        contentBinding.titleBar.text=title
        contentBinding.backBtn.visibility=if(contentBinding.mView.canGoBack()&&title!=requireArguments().getString(Intent.EXTRA_TITLE,""))View.VISIBLE else View.GONE
        if( contentBinding.backBtn.visibility==View.VISIBLE)
        {
            backDrawable=ViewUtils.setDrawableColor(color,backDrawable)
            contentBinding.backBtn?.setImageDrawable(backDrawable)
        }
    }
    var flag=false
    override fun processKeyBack(): Boolean {
        var result= super.processKeyBack()
        canGoBackHandle()
        return result
    }
    private fun canGoBackHandle()
    {
        if(!contentBinding.mView.canGoBack())
        {
            flag=false
            var title:String?=requireArguments().getString(Intent.EXTRA_TITLE,"")
            contentBinding.titleBar.text=title
            contentBinding.backBtn.visibility=View.GONE
        }
    }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden)
        {
            if(TextUtils.isEmpty(url))
            {
                url=resources.getString(R.string.youzan_storeurl)
            }
            flag=false
            contentBinding.mView.clearHistory()
            contentBinding.backBtn.visibility=View.GONE
            contentBinding.mView.loadUrl(url)
        }
    }
}