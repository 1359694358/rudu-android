package com.google.android.app.utils

import android.R
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager

fun RecyclerView.addVLayoutImpl():DelegateAdapter
{
    val layoutManager = VirtualLayoutManager(context)
    setLayoutManager(layoutManager)
    var recyclerViewAdapter= DelegateAdapter(layoutManager,true)
    adapter=recyclerViewAdapter
    var isOpened=false
    val activityRootView: View = ViewUtils.searchTintContextHostActivity(context).window.getDecorView().findViewById(R.id.content)
    activityRootView.viewTreeObserver.addOnGlobalLayoutListener {
        val heightDiff = activityRootView.rootView.height - activityRootView.height
        if (heightDiff > 100)
        { // 99% of the time the height diff will be due to a keyboard.
            if (!isOpened) {
                //Do two things, make the view top visible and the editText smaller
            }
            isOpened = true
            adapter?.notifyDataSetChanged()
        } else if (isOpened) {
            isOpened = false
            adapter?.notifyDataSetChanged()
        }
    }
    return recyclerViewAdapter
}