package com.chinamcloud.project.shanshipin.utils

import androidx.viewpager2.widget.ViewPager2
import net.lucode.hackware.magicindicator.MagicIndicator

fun ViewPager2.bindMagicIndicator(magicIndicator: MagicIndicator)
{
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            magicIndicator.onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            magicIndicator.onPageScrollStateChanged(state)
        }
    })
}