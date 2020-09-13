package com.rd.rudu.utils

import android.graphics.Color
import android.graphics.Rect
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tencent.liteav.demo.play.SuperPlayerModel
import com.tencent.liteav.demo.play.SuperPlayerView

object ListPlayerUtil
{
    private lateinit var playerView:SuperPlayerView
    private lateinit var recyclerView:RecyclerView
    private var playerContainer:ViewGroup?=null
    fun init(recyclerView:RecyclerView)
    {
        this.recyclerView=recyclerView
        recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener()
        {
            var mScrollState = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                mScrollState=newState
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(playerContainer==null || mScrollState == RecyclerView.SCROLL_STATE_IDLE)
                    return
                if(playerContainer?.isShown==false|| playerContainer?.getLocalVisibleRect(
                        Rect(
                            recyclerView.getLeft(),
                            recyclerView.getTop(),
                            recyclerView.getMeasuredWidth(),
                            recyclerView.getMeasuredHeight()
                        )
                    )==false)
                {
                    stopPlay()
                }
            }
        })
    }

    fun stopPlay()
    {
        if(::playerView.isInitialized)
        {
            playerView?.resetPlayer()
            if(playerView?.parent!=null)
            {
                (playerView?.parent as ViewGroup).removeView(playerView)
            }
            playerView.keepScreenOn=false
        }
    }

    private fun initPlayer()
    {
        if(!::playerView.isInitialized)
        {
            playerView= SuperPlayerView(recyclerView.context)
            playerView.setBackgroundColor(Color.BLACK)
            playerView.setPlayerViewCallback(object: SuperPlayerView.OnSuperPlayerViewCallback
            {
                override fun onClickSmallReturnBtn() {
                }

                override fun onStartFullScreenPlay() {
                }

                override fun onStopFullScreenPlay() {
                }

                override fun onClickFloatCloseBtn() {
                }

                override fun onStartFloatWindowPlay() {
                }

            })
        }
        else
        {
            stopPlay()
        }
    }

    fun playUrl(url:String,title:String,container:ViewGroup)
    {
        initPlayer()
        playerContainer=container
        var className=container.javaClass.name
        var layoutParamName="\$LayoutParams"
        var lp:ViewGroup.LayoutParams=container.javaClass.classLoader.loadClass("${className}$layoutParamName").asSubclass(ViewGroup.LayoutParams::class.java).getConstructor(Int::class.java,Int::class.java).newInstance(-1,-1)
        container.addView(playerView)
        playerView.layoutParams=lp
        val model= SuperPlayerModel()
        model.title=title
        model.url=url
        playerView.keepScreenOn=true
        playerView.playWithModel(model)
    }

}