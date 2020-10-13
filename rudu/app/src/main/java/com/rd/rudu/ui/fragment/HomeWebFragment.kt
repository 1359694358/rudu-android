package com.rd.rudu.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.app.utils.StatusBarUtil
import com.google.android.app.utils.ViewUtils
import com.rd.rudu.R
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class HomeWebFragment: YouZanWebFragment()
{
    val bgColor=Color.WHITE
    lateinit var backDrawable:Drawable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backDrawable=ViewUtils.setDrawableColor(requireActivity(),Color.BLACK, R.mipmap.icon_back_b)
        contentBinding.root.setBackgroundColor(bgColor)
        contentBinding.titleBar.setTextColor(Color.BLACK)
        contentBinding.backBtn?.setImageDrawable(backDrawable)
        StatusBarUtil.setColor(requireActivity(),bgColor ,0)
        StatusBarUtil.setLightMode(requireActivity())
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

    var flag=false
    override fun setFragmentTitle(title: CharSequence) {
        if(!flag)
        {
            flag=true
            return
        }
        if(title==requireActivity().getString(R.string.app_name))
        {
            contentBinding.titleBar.text=requireActivity().getString(R.string.home_title)
        }
        else
        {
            contentBinding.titleBar.text=title
        }
        contentBinding.backBtn.visibility=if(contentBinding.mView.canGoBack())View.VISIBLE else View.GONE
        renderColor()
    }

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
    private fun renderColor()
    {
        if( contentBinding.backBtn.visibility==View.VISIBLE)
        {
            backDrawable=ViewUtils.setDrawableColor(Color.BLACK,backDrawable)
            contentBinding.backBtn?.setImageDrawable(backDrawable)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden)
        {
            StatusBarUtil.setColor(requireActivity(),bgColor ,0)
            StatusBarUtil.setLightMode(requireActivity())
            renderColor()
        }
        else
        {
            StatusBarUtil.setColor(requireActivity(),Color.TRANSPARENT ,0)
            StatusBarUtil.setLightMode(requireActivity())
        }
    }
}