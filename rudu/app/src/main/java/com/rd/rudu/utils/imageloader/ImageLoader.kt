package com.rd.rudu.utils.imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import android.webkit.URLUtil
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory



object ImageLoader
{
    var loader: Loader
    var drawableCrossFadeFactory = DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build()
    init {
        loader = object : Loader {
            override fun clearMemoryCache(context:Context) {
                Glide.get(context).clearMemory()
            }

            override fun load(image: ImageView, url: String) {
                load(image, url, null)
            }

            override fun load(image: ImageView, url: String, placeDrawable: Drawable?) {
                load(image, url, placeDrawable, null)
            }

            override fun load(image: ImageView, url: String, placeDrawable: Drawable?, errorDrawable: Drawable?) {
                load(image, url, placeDrawable, errorDrawable ?: placeDrawable, null)
            }

            override fun load(image: ImageView, url: String, placeDrawable: Drawable?, errorDrawable: Drawable?, loadListener: LoadListener?) {
                var url = url
                val requestOption = RequestOptions().error(errorDrawable ?: placeDrawable).placeholder(placeDrawable).fallback(placeDrawable)
                if(!URLUtil.isNetworkUrl(url))
                {
                    if(placeDrawable!=null)
                    {
                        Glide.with(image).load(placeDrawable).into(image)
                    }
                    else if(errorDrawable!=null)
                    {
                        Glide.with(image).load(errorDrawable).into(image)
                    }
                    return
                }
                val glideUrl = GlideUrl(url)
                Glide.with(image).load(glideUrl).apply(requestOption).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        loadListener?.loadError(e?.message?:"")
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        loadListener?.loadComplete(resource)
                        return false
                    }
                }).transition(DrawableTransitionOptions.with(drawableCrossFadeFactory)).
                    into(image)
            }
        }
    }

    interface LoadListener {
        fun loadComplete(drawable: Drawable)
        fun loadError(msg: String)
    }

    interface Loader {
        fun clearMemoryCache(context:Context)
        fun load(image: ImageView, url: String)
        fun load(image: ImageView, url: String, placeDrawable: Drawable?)
        fun load(image: ImageView, url: String, placeDrawable: Drawable?, errorDrawable: Drawable?)
        fun load(image: ImageView, url: String, placeDrawable: Drawable?, errorDrawable: Drawable?, loadListener: LoadListener?)

    }
}
