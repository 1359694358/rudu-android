package com.rd.rudu.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.app.utils.readAny
import com.google.android.app.utils.saveAny
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.R
import com.rd.rudu.databinding.ActivityGuideBinding
import org.jetbrains.anko.startActivity

class GuideActivity: BaseActivity<ActivityGuideBinding>() {
    companion object
    {
        private const val WatchedGuide="WatchedGuide"

        fun needWatchGuide():Boolean
        {
            var watched:Boolean=readAny(WatchedGuide)?:true
            return watched
        }
    }
    override fun getLayoutResId(): Int {
        return R.layout.activity_guide
    }
    var startX = 0f
    var startY = 0f
    var endX = 0f
    var endY = 0f
    var currentItem=0
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var rudu_app_guide_res = intArrayOf(R.drawable.guide1,R.drawable.guide2,R.drawable.guide3)
        contentBinding.guideSwitch.adapter = object : RecyclerView.Adapter<ViewHolderX>() {
            override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
            ): ViewHolderX {
                return ViewHolderX(
                        ImageView(this@GuideActivity)
                )
            }

            override fun getItemCount(): Int {
                return rudu_app_guide_res.size
            }

            override fun onBindViewHolder(holder: ViewHolderX, position: Int) {
                holder.image.scaleType = ImageView.ScaleType.CENTER_CROP
                holder.image.adjustViewBounds=true
                holder.image.setImageResource(rudu_app_guide_res[position])
                if(position==(rudu_app_guide_res.size-1))
                {
                    holder.itemView.setOnClickListener {
                        startHome()
                    }
                }
            }
        }

        contentBinding.guideSwitch.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback()
        {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentItem=position
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                /*if(currentItem==(rudu_app_guide_res.size-1)&&(positionOffset==0F||positionOffsetPixels==0||positionOffset.toInt()==positionOffsetPixels))
                {
                    startHome()
                }*/
            }
        })
    }
    private fun startHome()
    {
        saveAny(WatchedGuide,false)
        startActivity<HomeActivity>()
        finish()
    }
    class ViewHolderX(var image: ImageView) : RecyclerView.ViewHolder(image)
    {
        init {
            image.layoutParams=RecyclerView.LayoutParams(-1,-1)
        }
    }

    override fun getFitSystemWindow(): Boolean {
        return false
    }

    override fun getStatusBarColor(): Int {
        return Color.TRANSPARENT
    }
}