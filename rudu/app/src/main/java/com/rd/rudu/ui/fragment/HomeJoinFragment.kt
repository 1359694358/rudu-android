package com.rd.rudu.ui.fragment

import android.os.Bundle
import android.view.View
import com.google.android.app.widget.BaseFragment
import com.rd.rudu.R
import com.rd.rudu.databinding.FragmentHomejoinBinding
class HomeJoinFragment: BaseFragment<FragmentHomejoinBinding>()
{
    override fun getLayoutResId(): Int
    {
        return R.layout.fragment_homejoin
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

    }
}