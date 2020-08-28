package com.rd.rudu.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.google.android.app.adapter.AppVLayoutAdapter
import com.google.android.app.adapter.BaseViewHolder
import com.rd.rudu.R
import com.rd.rudu.databinding.AdapterJoinCompanyBinding

class CompanyItemAdapter(context: Context,var layoutHelper: LinearLayoutHelper=LinearLayoutHelper(context.resources.getDimensionPixelOffset(R.dimen.dimen10))) : AppVLayoutAdapter<String>(context, layoutHelper) {
    init {
        layoutHelper.setAspectRatio(2.0f)
        layoutHelper.setBgColor(Color.YELLOW)
        layoutHelper.setAspectRatio(2.0f)
        layoutHelper.setMargin(10, 10, 10, 10)
        layoutHelper.setPadding(10, 10, 10, 10)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return CompanyItemHolder(R.layout.adapter_join_company,context)
    }
}


class CompanyItemHolder(layoutId: Int, context: Context?) : BaseViewHolder<AdapterJoinCompanyBinding>(layoutId, context)