package com.rd.rudu.bean.result;

import com.rd.rudu.ui.adapter.HomeJoinItemType;
import com.rd.rudu.ui.adapter.HomeJoinListAdapterKt;

import org.jetbrains.annotations.NotNull;

import kotlin.Pair;

//"id": 1,
//    "mainTitle": "招商加盟 合作共赢",
//    "subTitle": "誉都商城线上交易平台",
//    "bannerUrl": "https://join-1302533250.cos.ap-guangzhou.myqcloud.com/join_merchants/ed1bd240-3686-4861-a5a7-c8d53df31a32.png",
//    "topUrl": "https://join-1302533250.cos.ap-guangzhou.myqcloud.com/join_merchants/8cfffd69-0bd7-4e62-9960-c4557c11fbf2.png",
//    "title": "招商加盟",
//    "modifyTime": "2020-08-31 12:54:03"
public class JoinMerChantsBean extends BaseResultBean<JoinMerChantsBean.ChantsData>   implements HomeJoinItemType
{

    @NotNull
    @Override
    public Pair<Integer, Integer> getJoinItemType() {
        return HomeJoinListAdapterKt.JOIN_TYPE_IMAGE1;
    }
    public class ChantsData
    {
        public String id;
        public String mainTitle;
        public String subTitle;
        public String bannerUrl;
        public String topUrl;
        public String title;
        public String modifyTime;
    }
}
