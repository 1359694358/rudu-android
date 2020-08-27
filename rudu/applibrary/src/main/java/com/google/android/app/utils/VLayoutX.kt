package com.google.android.app.utils

import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager

fun RecyclerView.addVLayoutImpl():DelegateAdapter
{
    val layoutManager = VirtualLayoutManager(context)
    setLayoutManager(layoutManager)
    var recyclerViewAdapter= DelegateAdapter(layoutManager,true)
    adapter=recyclerViewAdapter
    return recyclerViewAdapter
}