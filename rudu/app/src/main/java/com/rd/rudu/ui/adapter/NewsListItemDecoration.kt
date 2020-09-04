package com.rd.rudu.ui.adapter

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rd.rudu.R

class NewsListItemDecoration: RecyclerView.ItemDecoration()
{

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0,view.resources.getDimensionPixelOffset(R.dimen.dimen5),0,0)
    }
}