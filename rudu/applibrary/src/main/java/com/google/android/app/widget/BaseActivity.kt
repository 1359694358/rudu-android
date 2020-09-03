package com.google.android.app.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.android.app.DefaultApp
import com.google.android.app.R
import com.google.android.app.databinding.AppToolbarBinding
import com.google.android.app.utils.StatusBarUtil
import com.qmuiteam.qmui.arch.QMUIActivity


abstract class BaseActivity<T: ViewDataBinding>: AppCompatActivity()
{
    lateinit var contentBinding:T
    var toolbarBinding: AppToolbarBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentBinding=DataBindingUtil.inflate(layoutInflater,getLayoutResId(),null,false)

        val contentFrameLayout = FrameLayout(this)
        val layoutParams = FrameLayout.LayoutParams(-1, -1)
        contentFrameLayout.addView(contentBinding.root, layoutParams)
        contentFrameLayout.fitsSystemWindows = getFitSystemWindow()
        setContentView(contentFrameLayout)
        contentBinding.root.fitsSystemWindows = getFitSystemWindow()
        contentFrameLayout.contentDescription = javaClass.simpleName
        window.decorView.contentDescription = "DecorView"
        setFit(contentFrameLayout)
        setStatusBarMode()
        setStatusBarColor()
        var toolbarBindingView: View?=contentBinding.root.findViewById(R.id.toolbarBindingView)
        if(toolbarBindingView!=null)
        {
            toolbarBinding=DataBindingUtil.findBinding(toolbarBindingView)
            toolbarBinding?.backBtn?.setOnClickListener {
                backHandle()
            }
            if(!getFitSystemWindow())
            {
                toolbarBinding?.root?.viewTreeObserver?.addOnDrawListener {
                    var lp=toolbarBinding?.root?.layoutParams
                    if(lp is ViewGroup.MarginLayoutParams)
                    {
                        lp.topMargin=StatusBarUtil.getStatusBarHeight(this)
                        toolbarBinding?.root?.requestLayout()
                    }
                }
            }
        }
    }

    protected open fun backHandle()
    {
        finish()
    }

    fun setTitle(title:String)
    {
        toolbarBinding?.titleText?.text=title
    }

    override fun setTitle(titleId: Int)
    {
        toolbarBinding?.titleText?.setText(titleId)
    }

    open fun setStatusBarMode()
    {
        StatusBarUtil.setLightMode(this)
    }

    open fun setStatusBarColor()
    {
        StatusBarUtil.setColor(this,getStatusBarColor() ,0)
    }
    open fun getStatusBarColor():Int
    {
        return ContextCompat.getColor(this, R.color.statusbar_color)
    }
    abstract fun getLayoutResId():Int

    protected fun <T: ViewModel> getViewModel(modelClazz:Class<T>): T
    {
        return ViewModelProviders.of(this).get(modelClazz)
    }

    protected fun <T: ViewModel> getViewModelByApplication(modelClazz:Class<T>): T
    {
        return (applicationContext as DefaultApp).getAppViewModelProvider(this).get(modelClazz)
    }
    override fun finish() {
        hideKeyBroad()
        super.finish()
    }
    protected fun hideKeyBroad()
    {
        val v = window.peekDecorView()
        if (v != null && v.windowToken != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    protected fun showKeyBroad(view:View)
    {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view,0)
    }
    protected fun killProcess()
    {
        Process.killProcess(Process.myPid())
    }

    open fun setFit(contentView: View) {
        var view = contentView
        while (view.parent !== window.decorView) {
            val parent = view.parent as View
            parent.fitsSystemWindows = getFitSystemWindow()
            view = parent
        }
    }

    protected open fun getFitSystemWindow(): Boolean {
        return true
    }


   /* @CallSuper
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        if(System.currentTimeMillis()<0)//肯定不会执行的 为了不代码显示错误
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        val fm = supportFragmentManager
        var fragments: List<Fragment?>? = null
        try {
            fragments = fm.fragments
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (fragments == null) return
        for (frag in fragments) {
            frag?.let { handlePermissonsResult(it, requestCode, permissions, grantResults) }
        }
    }

    fun handlePermissonsResult(
            frag: Fragment,
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        frag.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var frags: List<Fragment?>? = null
        try {
            frags = frag.childFragmentManager.fragments
        } catch (e: Exception) {
        }
        if (frags != null) {
            for (f in frags) {
                if (f != null && f.activity != null) handlePermissonsResult(
                        f,
                        requestCode,
                        permissions,
                        grantResults
                )
            }
        }
    }*/

    @CallSuper
    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        val fm = supportFragmentManager
        var fragments: List<Fragment?>? = null
        try {
            fragments = fm.fragments
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (fragments == null) return
        for (frag in fragments) {
            frag?.let { handleResult(it, requestCode, resultCode, data) }
        }
    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    fun handleResult(
            frag: Fragment, requestCode: Int, resultCode: Int,
            data: Intent?
    ) {
        if (frag.activity != null) frag.onActivityResult(
                requestCode and 0xffff,
                resultCode,
                data
        )
        var frags: List<Fragment?>? = null
        try {
            frags = frag.childFragmentManager.fragments
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (frags != null) {
            for (f in frags) {
                if (f != null && f.activity != null) handleResult(
                        f,
                        requestCode,
                        resultCode,
                        data
                )
            }
        }
    }
}