package com.rd.rudu.bean.request

import com.google.android.app.net.TransUtils


open class BaseRequestBody()
{
    override fun toString(): String
    {
        return TransUtils.gson.toJson(this)
    }
}