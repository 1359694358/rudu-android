package com.rd.rudu.ui.widget

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T: ViewDataBinding>() : Fragment()
{
    protected lateinit var contentBinding:T
    fun getRootFragment(): Fragment? {
        var fragment: Fragment? = parentFragment ?: return this
        while (fragment != null && fragment.parentFragment != null) {
            fragment = fragment.parentFragment
        }
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contentBinding= DataBindingUtil.inflate(layoutInflater,getLayoutResId(),null,false)
        return contentBinding.root
    }
    abstract fun getLayoutResId():Int
    override fun startActivityForResult(intent: Intent?, requestCode: Int)
    {
        if (getRootFragment() !== this) getRootFragment()?.startActivityForResult(intent, requestCode) else {
            super.startActivityForResult(intent, requestCode)
        }
    }
}