package com.rd.rudu.ui.adapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.rd.rudu.ui.activity.WebViewActivity

object AdapterExt
{
    @JvmStatic
    @BindingAdapter("clickOpenUrl")
    fun clickOpenUrl(view: View, clickOpenUrl:String?)
    {
        view.setOnClickListener {
            WebViewActivity.startActivity(view.context,clickOpenUrl)
        }
    }
}