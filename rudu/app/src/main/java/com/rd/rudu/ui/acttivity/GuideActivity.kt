package com.rd.rudu.ui.acttivity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.rd.rudu.R
import com.rd.rudu.databinding.ActivityGuideBinding
import com.google.android.app.widget.BaseActivity

class GuideActivity: BaseActivity<ActivityGuideBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_guide
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var rudu_app_guide_res = resources.getIntArray(R.array.rudu_app_guide_res)
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
                holder.image.setImageResource(rudu_app_guide_res[position])
            }
        }
    }
    class ViewHolderX(var image: ImageView) : RecyclerView.ViewHolder(image)
}