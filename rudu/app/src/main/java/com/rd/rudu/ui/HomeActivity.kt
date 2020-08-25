package com.rd.rudu.ui

import android.os.Handler
import com.rd.rudu.R
import com.rd.rudu.databinding.ActivityHomeBinding
import com.rd.rudu.ui.widget.BaseActivity
import com.rd.rudu.utils.ToastUtil

class HomeActivity: BaseActivity<ActivityHomeBinding>() {
    val Interval=3000
    var time = System.currentTimeMillis()-Interval
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
}