package com.rd.rudu.ui.acttivity

import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.app.utils.ToastUtil
import com.rd.rudu.R
import com.rd.rudu.databinding.ActivityHomeBinding
import com.google.android.app.widget.BaseActivity
import com.rd.rudu.ui.fragment.HomeJoinFragment
import com.rd.rudu.ui.fragment.HomeWebFragment

class HomeActivity: BaseActivity<ActivityHomeBinding>() {
    val Interval=3000
    var time = System.currentTimeMillis()-Interval
    var fragmentMap= mutableMapOf<Int,Fragment>()
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
        pushFragment()
        checkedTableIndex(0)
        addCheckedListener()
    }

    private fun pushFragment()
    {
        fragmentMap[0]=HomeWebFragment()
        fragmentMap[1]= HomeJoinFragment()
        fragmentMap[2]= HomeJoinFragment()
        fragmentMap[3]= HomeJoinFragment()
        fragmentMap[4]= HomeJoinFragment()
        var iterator=fragmentMap.iterator()
        var tran=supportFragmentManager.beginTransaction()
        while (iterator.hasNext())
        {
            var item=iterator.next()
            tran.add(R.id.fragmentContainer,item.value)
        }
        tran.commitAllowingStateLoss()
        chooseFragment(0)
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
            var fragment=item.value
            if(fgIndex==index)
            {
                if(fragment.isHidden)
                    supportFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss()
            }
            else
            {
                if(!fragment.isHidden)
                    supportFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss()
            }
        }
    }
}