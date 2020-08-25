package com.rd.rudu.ui.widget

import android.content.Intent
import androidx.fragment.app.Fragment

class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId)
{
    fun getRootFragment(): Fragment? {
        var fragment: Fragment? = parentFragment ?: return this
        while (fragment != null && fragment.parentFragment != null) {
            fragment = fragment.parentFragment
        }
        return fragment
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int)
    {
        if (getRootFragment() !== this) getRootFragment()?.startActivityForResult(intent, requestCode) else {
            super.startActivityForResult(intent, requestCode)
        }
    }
}