package com.rd.rudu.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.core.text.set
import com.google.android.app.utils.StatusBarUtil
import com.google.android.app.utils.ToastUtil
import com.google.android.app.widget.BaseActivity
import com.qmuiteam.qmui.span.QMUITouchableSpan
import com.rd.rudu.R
import com.rd.rudu.databinding.ActivityLoginlayoutBinding

class LoginActivity: BaseActivity<ActivityLoginlayoutBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_loginlayout
    }

    override fun getStatusBarColor(): Int {
        return Color.TRANSPARENT
    }

    override fun getFitSystemWindow(): Boolean {
        return false
    }

    override fun setStatusBarMode() {
        StatusBarUtil.setDarkMode(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var agreeSpan=SpannableStringBuilder(resources.getString(R.string.agree_str))
        var star=agreeSpan.length-8
        var end=agreeSpan.length
        agreeSpan[star,end]= ClickTouchSpan()
        contentBinding.appAgree.setLinkUnderLineColor(Color.WHITE)
        contentBinding.appAgree.setLinkUnderLineHeight(2)
        contentBinding.appAgree.text=agreeSpan
    }

    inner class ClickTouchSpan: QMUITouchableSpan(Color.WHITE,Color.WHITE,Color.TRANSPARENT,Color.TRANSPARENT)
    {
        init {
            setIsNeedUnderline(true)
        }
        override fun onSpanClick(widget: View?) {
            var use_terms=resources.getString(R.string.use_terms)
            var cacheDir=cacheDir.absolutePath
            var path="${cacheDir}${use_terms}"
            OfficeFileViewActivity.startActivity(this@LoginActivity,path,"用户使用条款")
        }

    }
}
