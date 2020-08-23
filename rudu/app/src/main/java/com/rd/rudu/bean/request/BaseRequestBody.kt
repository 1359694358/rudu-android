package com.rd.rudu.bean.request

import com.rd.rudu.net.TransUtils


open class BaseRequestBody()
{
    override fun toString(): String
    {
        return TransUtils.gson.toJson(this)
    }
}