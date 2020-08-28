package com.google.android.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder
{
    public T contentViewBinding;
    public BaseViewHolder(@LayoutRes int layoutId, Context context)
    {
        super(DataBindingUtil.inflate(ContextCompat.getSystemService(context, LayoutInflater.class),layoutId,null,false).getRoot());
        contentViewBinding=DataBindingUtil.getBinding(itemView);
    }
}
