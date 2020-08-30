package com.rd.rudu.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.google.android.app.utils.StatusBarUtil
import com.google.android.app.widget.BaseFragment
import com.rd.rudu.R
import com.rd.rudu.databinding.FragmentHomemineBinding

class HomeMineFragment: BaseFragment<FragmentHomemineBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_homemine
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentBinding.titleBar.viewTreeObserver.addOnDrawListener {
            if(contentBinding.titleBar.layoutParams!=null&&contentBinding.titleBar.layoutParams is ViewGroup.MarginLayoutParams)
            {
                (contentBinding.titleBar.layoutParams as ViewGroup.MarginLayoutParams).topMargin= StatusBarUtil.getStatusBarHeight(requireActivity())
                contentBinding.titleBar.requestLayout()
            }
        }
    }
}