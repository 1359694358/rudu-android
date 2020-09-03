package com.google.android.app.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<B : ViewDataBinding>(
    var context: Context,
    @LayoutRes layoutId: Int,
    widthMatchParent:Boolean=false
) : RecyclerView.ViewHolder(DataBindingUtil.inflate<ViewDataBinding>(ContextCompat.getSystemService(context, LayoutInflater::class.java)!!, layoutId, null, false).root) {
    var contentViewBinding: B = DataBindingUtil.getBinding(itemView)!!
    init {
        if(widthMatchParent)
        {
            itemView.layoutParams=RecyclerView.LayoutParams(-1,-2)
        }
    }
    protected fun getLayoutInflater():LayoutInflater
    {
        return ContextCompat.getSystemService(context, LayoutInflater::class.java)!!
    }
}

open class BaseViewHolderMP<B:ViewDataBinding>(context: Context, layoutId: Int, widthMatchParent: Boolean = true) : BaseViewHolder<B>(context, layoutId, widthMatchParent)