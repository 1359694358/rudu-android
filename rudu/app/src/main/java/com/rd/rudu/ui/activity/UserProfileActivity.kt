package com.rd.rudu.ui.activity

import android.os.Bundle
import android.widget.TextView
import com.google.android.app.utils.ViewUtils
import com.google.android.app.widget.BaseActivity
import com.google.android.app.widget.datepicker.CustomDatePicker
import com.google.android.app.widget.datepicker.DateFormatUtils
import com.rd.rudu.R
import com.rd.rudu.bean.result.LoginResultBean
import com.rd.rudu.databinding.ActivityPersonalInfoBinding
import com.rd.rudu.vm.UserViewModel

class UserProfileActivity: BaseActivity<ActivityPersonalInfoBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_personal_info
    }

    val userViewModel:UserViewModel by lazy { getViewModelByApplication<UserViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        title = "个人信息"
        setMoreText("保存")
        var color=0xFF222222.toInt()
        toolbarBinding?.titleText?.setTextColor(color)
        toolbarBinding?.moreText?.setTextColor(color)
        toolbarBinding?.backBtn?.setImageDrawable(ViewUtils.setDrawableColor(this,color,R.mipmap.icon_back_b))
        var loginResult= LoginResultBean.LoginResult.getLoginResult()

        contentBinding.personalInfoLayout.tvEditDetailRightBirthday.setOnClickListener {
            // 日期格式为yyyy-MM-dd
            var currentDate=(it as TextView).text.toString()
            if(currentDate.isEmpty())
            {
                currentDate=DateFormatUtils.long2Str(System.currentTimeMillis(), false)
            }
            mDatePicker.show(currentDate)
        }
        initDatePicker()
    }

    lateinit var mDatePicker:CustomDatePicker
    fun initDatePicker()
    {
        val beginTimestamp:Long = DateFormatUtils.str2Long("1900-01-01", false)
        val endTimestamp = System.currentTimeMillis()

//        contentBinding.personalInfoLayout.tvEditDetailRightBirthday.setText(DateFormatUtils.long2Str(endTimestamp, false))

        // 通过时间戳初始化日期，毫秒级别

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = CustomDatePicker(this,
                CustomDatePicker.Callback { timestamp ->
                    contentBinding.personalInfoLayout.tvEditDetailRightBirthday.setText(DateFormatUtils.long2Str(timestamp, false))
                },
                beginTimestamp,
                endTimestamp)
        // 不允许点击屏幕或物理返回键关闭
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false)
        // 不显示时和分
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false)
        // 不允许循环滚动
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false)
        // 不允许滚动动画
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDatePicker.onDestroy()
    }
}