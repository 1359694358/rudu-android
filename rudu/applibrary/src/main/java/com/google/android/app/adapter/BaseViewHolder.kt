package com.google.android.app.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.google.android.app.utils.imageloader.ImageLoader

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
    companion object
    {
        @JvmStatic
        @BindingAdapter(value = ["url","res" ],requireAll = false)
        fun loadImage(image:ImageView,url:String?,@DrawableRes res:Int)
        {
            var drawable:Drawable?=null
            try
            {
                drawable=ContextCompat.getDrawable(image.context,res?:0)
            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }
            if(URLUtil.isNetworkUrl(url))
            {
                ImageLoader.loader.load(image,url?.toString()?:"",drawable)
            }
            else
            {
                image.setImageDrawable(drawable)
            }
        }
    }
}

open class BaseViewHolderMP<B:ViewDataBinding>(context: Context, layoutId: Int, widthMatchParent: Boolean = true) : BaseViewHolder<B>(context, layoutId, widthMatchParent)