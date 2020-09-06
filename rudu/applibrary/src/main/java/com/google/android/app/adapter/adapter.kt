package com.mediacloud.app.style.dahe.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

interface RVItemType
{
    fun getRVItemType():Int
}

abstract class VH<T:ViewDataBinding,DATA:RVItemType>(var binding: T):RecyclerView.ViewHolder(binding.root)
{
    abstract fun update(data:DATA)
}

abstract class RVAdapter(var context:Context): Adapter<RecyclerView.ViewHolder>()
{
    var dataList= mutableListOf<RVItemType>()

    override fun getItemCount(): Int
    {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].getRVItemType()
    }

    fun <T:RVItemType> getItem(position: Int): T?
    {
        if(dataList.size>0&&position<dataList.size)
            return dataList[position] as T
        return null
    }
}