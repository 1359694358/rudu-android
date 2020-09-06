package com.rd.rudu.bean.result;

import com.rd.rudu.ui.adapter.HomeJoinItemType;
import com.rd.rudu.ui.adapter.HomeJoinListAdapterKt;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kotlin.Pair;

//{
//  "code": 0,
//  "data": [
//    {
//      "id": 6,
//      "name": "轮播1",
//      "picUrl": "https://join-1302533250.cos.ap-guangzhou.myqcloud.com/join_swipers/e57b2280-f5bb-4236-b5d2-60eb9f71e3fa.jpg",
//      "linkUrl": "https://www.youzan.com/",
//      "modifyTime": "2020-08-29 12:13:20"
//    },
//    {
//      "id": 10,
//      "name": "轮播5",
//      "picUrl": "https://join-1302533250.cos.ap-guangzhou.myqcloud.com/join_swipers/7eea08f8-ba06-4d3d-8955-4fb18b9011e0.jpg",
//      "linkUrl": "https://www.youzan.com/",
//      "modifyTime": "2020-08-29 12:23:34"
//    }
//  ],
//  "msg": null
//}
public class JoinBannerResultBean extends BaseResultBean<List<JoinBannerResultBean.JoinBannerItem>>  implements HomeJoinItemType
{

    public static class JoinBannerItem
    {
        public String id;
        public String name;
        public String picUrl;
        public String linkUrl;
        public String modifyTime;

    }
    @NotNull
    @Override
    public Pair<Integer, Integer> getJoinItemType()
    {
        return HomeJoinListAdapterKt.JOIN_TYPE_BANNER;
    }
}