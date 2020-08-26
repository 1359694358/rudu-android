package com.rd.rudu.utils

import android.content.Context
import android.os.Build
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebView.setWebContentsDebuggingEnabled

fun WebView.setWebSetting() {
    if (Build.VERSION.SDK_INT >= 17) {
        getSettings().setMediaPlaybackRequiresUserGesture(false)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        getSettings().setSafeBrowsingEnabled(false)
    }
    if (Build.VERSION.SDK_INT >= 19)
        setWebContentsDebuggingEnabled(true)
    getSettings().setDatabaseEnabled(true)
    setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
    val dir: String =getContext().getDir("database", Context.MODE_PRIVATE).getPath()
    getSettings().setGeolocationDatabasePath(dir)
    getSettings().setGeolocationEnabled(true)
    getSettings().setDomStorageEnabled(true)
    getSettings().setJavaScriptEnabled(true)
    getSettings().setAllowFileAccess(true) // 允许访问文件
    getSettings().setDefaultTextEncodingName("UTF-8")
    //        getSettings().setAppCacheEnabled(false);
    getSettings().setSavePassword(false)
    getSettings().setCacheMode(WebSettings.LOAD_DEFAULT)
    if (Build.VERSION.SDK_INT >= 21) {
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    }
}

fun com.tencent.smtt.sdk.WebView.setWebSetting() {
    if (Build.VERSION.SDK_INT >= 17) {
        getSettings().setMediaPlaybackRequiresUserGesture(false)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        getSettings().setSafeBrowsingEnabled(false)
    }
    if (Build.VERSION.SDK_INT >= 19)
        com.tencent.smtt.sdk.WebView.setWebContentsDebuggingEnabled(true)
    getSettings().setDatabaseEnabled(true)
    setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
    val dir: String =getContext().getDir("database", Context.MODE_PRIVATE).getPath()
    getSettings().setGeolocationDatabasePath(dir)
    getSettings().setGeolocationEnabled(true)
    getSettings().setDomStorageEnabled(true)
    getSettings().setJavaScriptEnabled(true)
    getSettings().setAllowFileAccess(true) // 允许访问文件
    getSettings().setDefaultTextEncodingName("UTF-8")
    //        getSettings().setAppCacheEnabled(false);
    getSettings().setSavePassword(false)
    getSettings().setCacheMode(WebSettings.LOAD_DEFAULT)
    if (Build.VERSION.SDK_INT >= 21) {
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    }
}