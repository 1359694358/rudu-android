package com.rd.rudu.ui

import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rd.rudu.R
import com.rd.rudu.databinding.ActivityHomeBinding
import com.rd.rudu.ui.widget.BaseActivity
import com.rd.rudu.utils.ToastUtil

class HomeActivity: BaseActivity<ActivityHomeBinding>() {
    val Interval=3000
    var time = System.currentTimeMillis()-Interval
    var fragmentMap= mapOf<Int,Fragment>()
    override fun getLayoutResId(): Int {
        return R.layout.activity_home
    }
    override fun onBackPressed() {
        backHandle()
    }

    override fun backHandle() {
        var temp = System.currentTimeMillis()
        if (temp - time > Interval) {
            ToastUtil.show(this, "再按一次退出")
            time = temp
        } else {
            finish()
            //延时直接结束程序进程
            Handler().postDelayed({
                killProcess()
            },200);
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkedTableIndex(0)
        addCheckedListener()
    }

    private fun addCheckedListener()
    {
        var bottomLayout:ViewGroup=findViewById(R.id.bottomLayout)
        for( layerIndex in 0 until bottomLayout.childCount)
        {
            bottomLayout.getChildAt(layerIndex).setOnClickListener {
                checkedTableIndex(layerIndex)
            }
        }
    }
    private fun checkedTableIndex(index:Int)
    {
        chooseFragment(index)
        var bottomLayout:ViewGroup=findViewById(R.id.bottomLayout)
        for( layerIndex in 0 until bottomLayout.childCount)
        {
            var isSelected=index==layerIndex
            bottomLayout.getChildAt(layerIndex).isSelected=isSelected

        }
    }

    private fun chooseFragment(index:Int)
    {
        var iterator=fragmentMap.iterator()
        while (iterator.hasNext())
        {
            var item=iterator.next()
            var fgIndex=item.key
        }
    }
}