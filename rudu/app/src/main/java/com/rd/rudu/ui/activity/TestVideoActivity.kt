package com.rd.rudu.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.databinding.ActivityTestvideoBinding
import com.tencent.liteav.demo.play.SuperPlayerModel
import org.jetbrains.anko.startActivity

class TestVideoActivity: BaseActivity<ActivityTestvideoBinding>()
{

    companion object
    {
        fun Context.startVideoActivity(title:String,url:String)
        {
            startActivity<TestVideoActivity>(Pair(Intent.EXTRA_TITLE,title),Pair(Intent.EXTRA_ORIGINATING_URI,url))
        }
    }
    override fun getLayoutResId(): Int
    {
        return R.layout.activity_testvideo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model= SuperPlayerModel()
        model.url=intent.getStringExtra(Intent.EXTRA_ORIGINATING_URI)
        model.title=intent.getStringExtra(Intent.EXTRA_TITLE)
        contentBinding.videoPlayer.playWithModel(model)
    }
}