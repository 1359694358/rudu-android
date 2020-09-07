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
    var layoutType:Int=None
) : RecyclerView.ViewHolder(DataBindingUtil.inflate<ViewDataBinding>(ContextCompat.getSystemService(context, LayoutInflater::class.java)!!, layoutId, null, false).root) {

    var contentViewBinding: B = DataBindingUtil.getBinding(itemView)!!
    init {
        if(layoutType==WidthMatchParent)
        {
            itemView.layoutParams=RecyclerView.LayoutParams(-1,-2)
        }
        else if(layoutType== HeightMatchParent)
        {
            itemView.layoutParams=RecyclerView.LayoutParams(-2,-1)
        }
    }
    protected fun getLayoutInflater():LayoutInflater
    {
        return ContextCompat.getSystemService(context, LayoutInflater::class.java)!!
    }
    companion object
    {
        const val None=0
        const val WidthMatchParent=1
        const val HeightMatchParent=2
        const val AllMatchParent=3
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
                e.message
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
