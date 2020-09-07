package com.google.android.app.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DHQRecyclerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr)
{
    override fun onMeasure(widthSpec: Int, heightSpec: Int)
    {
        super.onMeasure(widthSpec, heightSpec)
        if(childCount==0||adapter==null||adapter!!.itemCount==0)
            return
        var childHeight=0
        for(index in 0 until childCount)
        {
            childHeight+=getChildAt(index).measuredHeight
        }
        if(childHeight>0&&childHeight<(parent as ViewGroup).measuredHeight)
        {
            val width = MeasureSpec.getSize(widthSpec)
            var heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY)
            super.onMeasure(widthSpec, heightMeasureSpec)
            setMeasuredDimension(width,childHeight)
        }
    }
}