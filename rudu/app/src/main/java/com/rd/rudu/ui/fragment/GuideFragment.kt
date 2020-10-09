package com.rd.rudu.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.app.utils.saveAny
import com.google.android.app.widget.BaseFragment
import com.rd.rudu.R
import com.rd.rudu.databinding.ActivityGuideBinding
import com.rd.rudu.ui.activity.GuideActivity
import com.rd.rudu.ui.activity.HomeActivity
import org.jetbrains.anko.startActivity

class GuideFragment: BaseFragment<ActivityGuideBinding>()
{
    override fun getLayoutResId(): Int
    {
        return R.layout.activity_guide
    }
    var startX = 0f
    var startY = 0f
    var endX = 0f
    var endY = 0f
    var currentItem=0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var rudu_app_guide_res = intArrayOf(R.drawable.guide1,R.drawable.guide2,R.drawable.guide3)
        contentBinding.guideSwitch.adapter = object : RecyclerView.Adapter<ViewHolderX>() {
            override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
            ): ViewHolderX
            {
                return ViewHolderX(
                        ImageView(requireActivity())
                )
            }

            override fun getItemCount(): Int
            {
                return rudu_app_guide_res.size
            }

            override fun onBindViewHolder(holder: ViewHolderX, position: Int) {
                if(position==0)
                {
                    holder.image.scaleType = ImageView.ScaleType.FIT_XY
                }
                else
                {
                    holder.image.scaleType = ImageView.ScaleType.CENTER_CROP
                }
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
                if(currentItem==(rudu_app_guide_res.size-1))
                {
                    contentBinding.skipGuide.visibility= View.VISIBLE
                }
                else
                {
                    contentBinding.skipGuide.visibility=View.GONE
                }
            }
        })
    }
    private fun startHome()
    {
        saveAny(GuideActivity.WatchedGuide,false)
        requireActivity().startActivity<HomeActivity>()
        requireActivity().finish()
    }
    class ViewHolderX(var image: ImageView) : RecyclerView.ViewHolder(image)
    {
        init {
            image.layoutParams=RecyclerView.LayoutParams(-1,-1)
        }
    }
}