package com.rd.rudu.ui.adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by x on 2018/1/24.
 */

public class GridStyleItemDecoration extends RecyclerView.ItemDecoration
{
    protected int dividerSize;

    public GridStyleItemDecoration(int dividerSize)
    {
        this.dividerSize=dividerSize;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {

        int oneRowCount=((GridLayoutManager)parent.getLayoutManager()).getSpanCount();
        int totalRow=parent.getAdapter().getItemCount()%oneRowCount==0?parent.getAdapter().getItemCount()/oneRowCount:(parent.getAdapter().getItemCount()/oneRowCount)+1;
        int position=parent.getChildAdapterPosition(view);
        int currentRowIndex=(position+1)%oneRowCount==0?(position+1)/oneRowCount:((position+1)/oneRowCount)+1;
        int top=0;
        int bottom=0;
        if(currentRowIndex!=totalRow)
        {
            bottom=dividerSize/2;
        }
        if(currentRowIndex!=1)//&&currentRowIndex!=totalRow
        {
            top=dividerSize/2;
        }
        int currentColumIndex=position-((currentRowIndex-1)*oneRowCount)+1;
        int left=0,right=0;
        if(currentColumIndex==1)//竖向第一列右侧
        {
            right=dividerSize/2;
        }
        else if(currentColumIndex==oneRowCount)//竖向非第一列左侧
        {
            left=dividerSize/2;
        }
        else //竖向非第一列最后一列左右侧
        {
            left=dividerSize/2;
            right=dividerSize/2;
        }
        /*if((position+1)%oneRowCount!=0)
        {
            outRect.set(left,top,right,bottom);
        }
        else*/
            outRect.set(left,top,right,bottom);
    }
}