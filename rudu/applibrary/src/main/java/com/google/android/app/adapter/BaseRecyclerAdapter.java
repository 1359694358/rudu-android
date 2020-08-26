package com.google.android.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.app.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter <T> extends RecyclerView.Adapter implements View.OnClickListener
{
    protected WeakReference<Context> contextRef;
    protected ItemClickListener<T> clickListener;

    public interface ItemClickListener<T>
    {
        void onItemClick(int index, boolean isFromAdaptor, BaseRecyclerAdapter<T> adapter);
    }

    public void setItemClickListener(ItemClickListener<T> listener) {
        clickListener = listener;
    }


    @Override
    @CallSuper
    public void onClick(View v)
    {
        Object tag = v.getTag(R.id.recyclerItemTag);
        if (tag == null)
            return;
        int position = tag != null ? (int) tag : 0;
        if (clickListener != null) {
            clickListener.onItemClick(position, true, this);
        }
        onItemClick(position, getItem(position));
    }

    protected void onItemClick(int position, T item)
    {

    }

    List<T> data;

    public BaseRecyclerAdapter(Context context) {
        this(new ArrayList<T>(),context);
    }

    public BaseRecyclerAdapter(List<T> data, Context context) {
        this.contextRef=new WeakReference<>(context);
        this.data = data;
    }
    public final Context getContext()
    {
        return contextRef.get();
    }
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        if (data != null)
            this.data = data;
    }

    @Override
    public int getItemCount() {
        if (data != null)
            return data.size();
        return 0;
    }

    public T getItem(int position) {
        if (data != null && getItemCount() > 0 && position < getItemCount())
            return data.get(position);
        return null;
    }
    @CallSuper
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        holder.itemView.setTag(R.id.recyclerItemTag,position);
        holder.itemView.setTag(R.id.recyclerItemData,getItem(position));
        if(holder!=null&&!holder.itemView.hasOnClickListeners())
        {
            holder.itemView.setOnClickListener(this);
        }
    }
    public View inflate(@LayoutRes int layoutResId, ViewGroup parent)
    {
        return LayoutInflater.from(getContext()).inflate(layoutResId,parent,false);
    }

    public void add(T t) {
        if (data == null) data = new ArrayList<>();
        data.add(t);
    }

    public void addAll(List<T> list) {
        if (data == null) data = new ArrayList<>();
        data.addAll(list);
    }
}