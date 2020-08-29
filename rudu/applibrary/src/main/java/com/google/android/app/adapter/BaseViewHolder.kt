package com.google.android.app.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<B : ViewDataBinding>(@LayoutRes layoutId: Int,var context: Context) : RecyclerView.ViewHolder(DataBindingUtil.inflate<ViewDataBinding>(ContextCompat.getSystemService(context, LayoutInflater::class.java)!!, layoutId, null, false).root) {
    var contentViewBinding: B = DataBindingUtil.getBinding(itemView)!!

    protected fun getLayoutInflater():LayoutInflater
    {
        return ContextCompat.getSystemService(context, LayoutInflater::class.java)!!
    }

}