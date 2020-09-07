package com.google.android.app.widget

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.android.app.DefaultApp
import com.google.android.app.R
import com.google.android.app.databinding.AppQmuiLoadingBinding

abstract class BaseFragment<T: ViewDataBinding>() : Fragment() {
    protected lateinit var contentBinding: T
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
        Log.w("AppFragment", "onCreateView: ${javaClass.simpleName}" )
        contentBinding = DataBindingUtil.inflate(layoutInflater, getLayoutResId(), null, false)
        return contentBinding.root
    }

    abstract fun getLayoutResId(): Int
    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        if (getRootFragment() !== this) getRootFragment()?.startActivityForResult(intent, requestCode) else {
            super.startActivityForResult(intent, requestCode)
        }
    }

    protected fun <T : ViewModel> getViewModel(modelClazz: Class<T>): T {
        return ViewModelProviders.of(this).get(modelClazz)
    }

    protected fun <T : ViewModel> getViewModelByApplication(modelClazz: Class<T>): T {
        return (requireActivity().applicationContext as DefaultApp).getAppViewModelProvider(requireActivity()).get(modelClazz)
    }

    private lateinit var loadingView:AppQmuiLoadingBinding

    fun showLoading()
    {
        if(!this::loadingView.isInitialized)
        {
            loadingView= AppQmuiLoadingBinding.inflate(layoutInflater, view as ViewGroup?,true)
        }
        loadingView.root.visibility=View.VISIBLE
        if(loadingView.root.layoutParams!=null&&loadingView.root.layoutParams is ViewGroup.MarginLayoutParams)
        {
            var lp = loadingView.root.layoutParams as ViewGroup.MarginLayoutParams
            lp.topMargin=resources.getDimensionPixelOffset(R.dimen.dimen42)
            loadingView.root.requestLayout()
        }
    }

    fun hideLoading()
    {
        if(!this::loadingView.isInitialized)
        {
            loadingView= AppQmuiLoadingBinding.inflate(layoutInflater, view as ViewGroup?,true)
        }
        loadingView.root.visibility=View.GONE
    }

}