package com.google.android.app.widget

import android.view.ViewGroup
import androidx.annotation.StringRes
import com.google.android.app.databinding.SimpleDialogBinding

fun SimpleDialogBinding.show(parent:ViewGroup)
{
    parent.addView(this.root)
}

fun SimpleDialogBinding.dissmiss()
{
    if(this.root.parent!=null)
    {
        (this.root.parent as ViewGroup).removeView(this.root)
    }
}

fun SimpleDialogBinding.setMainTitle(value:CharSequence)
{
    title1.text = value
}

fun SimpleDialogBinding.setMainTitle(@StringRes value:Int)
{
    title1.setText(value)
}

fun SimpleDialogBinding.setSubTitle(value:CharSequence)
{
    title2.text = value
}

fun SimpleDialogBinding.setSubTitle(@StringRes value:Int)
{
    title2.setText(root.context.getString(value))
}

fun SimpleDialogBinding.setSummary(value:CharSequence)
{
    summary.text = value
}

fun SimpleDialogBinding.setSummary(@StringRes value:Int)
{
    summary.setText(root.context.getString(value))
}

fun SimpleDialogBinding.setSureClickListener(call:()->Unit)
{
    sure.setOnClickListener {
        call()
        dissmiss()
    }
}

fun SimpleDialogBinding.setCancelClickListener(call:()->Unit)
{
    cancel.setOnClickListener {
        call()
        dissmiss()
    }
}