package com.rd.rudu.bean.request

//0：男，1：女
data class UpdateUserInfo(var id:String,var nickName:String,var gender:String,var avatar:String,var birthday:String):BaseRequestBody()