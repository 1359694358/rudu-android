package com.rd.rudu.ui.fragment

import android.os.Bundle
import android.view.View
import com.google.android.app.widget.BaseFragment
import com.rd.rudu.R
import com.rd.rudu.databinding.FragmentHomeyouzanBinding
//首页有赞
class HomeWebFragment: BaseFragment<FragmentHomeyouzanBinding>()
{
    override fun getLayoutResId(): Int
    {
        return R.layout.fragment_homeyouzan
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}