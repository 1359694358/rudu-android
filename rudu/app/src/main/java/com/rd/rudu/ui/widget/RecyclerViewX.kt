package com.rd.rudu.ui.widget

import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.VirtualLayoutManager

fun RecyclerView.addVLayoutImpl()
{
    val lm = VirtualLayoutManager(context)
    val recyclerPool=RecyclerView.RecycledViewPool()
    this.layoutManager=lm
}