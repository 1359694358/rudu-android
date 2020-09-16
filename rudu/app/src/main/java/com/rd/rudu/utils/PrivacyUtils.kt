package com.rd.rudu.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Process
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.set
import com.google.android.app.databinding.SimpleDialogBinding
import com.google.android.app.utils.readAny
import com.google.android.app.utils.saveAny
import com.google.android.app.widget.*
import com.qmuiteam.qmui.span.QMUITouchableSpan
import com.rd.rudu.R
import com.rd.rudu.ui.activity.OfficeFileViewActivity

object PrivacyUtils
{
    private const val Privacy="Privacy"
    fun showPrivacy(context: Activity,sureCall:()->Unit): Boolean
    {
        var hadShow= readAny<Boolean>(Privacy)?:false
        if(hadShow)
        {
            return true
        }
        var simpleDialogBinding=SimpleDialogBinding.inflate(context.layoutInflater)
        simpleDialogBinding.setMainTitle(R.string.privacytitle)
        var privacy=context.getString(R.string.privacy)
        var star=privacy.lastIndexOf(context.getString(R.string.user_note))
        var agreeSpan= SpannableStringBuilder(privacy)
        var end=star+context.getString(R.string.user_note).length
        agreeSpan[star,end]=PrivacySpan {
            var use_terms=context.getString(R.string.use_terms)
            var cacheDir=context.cacheDir.absolutePath
            var path="${cacheDir}${use_terms}"
            OfficeFileViewActivity.startActivity(context,path,"用户使用条款")
        }
        star=privacy.lastIndexOf(context.getString(R.string.privacy_info))
        end=star+context.getString(R.string.privacy_info).length
        agreeSpan[star,end]= PrivacySpan{
            var use_terms=context.getString(R.string.app_policy)
            var cacheDir=context.cacheDir.absolutePath
            var path="${cacheDir}${use_terms}"
            OfficeFileViewActivity.startActivity(context,path,"隐私政策")
        }
        simpleDialogBinding.setSubTitle(agreeSpan)
        simpleDialogBinding.show(context.window.decorView as ViewGroup)
        simpleDialogBinding.setCancelClickListener {
            Process.killProcess(Process.myPid())
        }
        simpleDialogBinding.setSureClickListener {
            sureCall()
            saveAny(Privacy,true)
        }
        return false
    }

    internal class PrivacySpan(var clickCall:()->Unit):QMUITouchableSpan(0xFF047BE4.toInt(), 0xFF047BE4.toInt(), Color.TRANSPARENT, Color.TRANSPARENT)
    {
        override fun onSpanClick(widget: View?)
        {
            clickCall()
        }
    }
}