package com.rd.rudu.bean.result;

import com.rd.rudu.ui.adapter.HomeJoinItemType;
import com.rd.rudu.ui.adapter.HomeJoinListAdapterKt;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import kotlin.Pair;

//{
//  "code": 0,
//  "data": {
//    "id": 1,
//    "title": "品牌招商入驻",
//    "bannerUrl": "https://join-1302533250.cos.ap-guangzhou.myqcloud.com/join_partner/4d6d1380-8585-4fe6-958f-2b6bab199726.png",
//    "topTitle": "越早驻入 越快赚钱",
//    "topUrl": "https://join-1302533250.cos.ap-guangzhou.myqcloud.com/join_partner/4d6d1380-8585-4fe6-958f-2b6bab199726.png",
//    "bgl": "163.4",
//    "fkl": "163.4",
//    "introTitle": "平台优势",
//    "introOneTitle": "提升曝光",
//    "introOneDesc": "多平台流量引入，多维度 曝光，引流快人一步",
//    "intrOnePic": null,
//    "introTwoTitle": "双向共赢",
//    "introTwoDesc": "多平台流量引入，多维度 曝光，引流快人一步",
//    "introTwoPic": "https://join-1302533250.cos.ap-guangzhou.myqcloud.com/join_partner/4d6d1380-8585-4fe6-958f-2b6bab199726.png",
//    "modifyTime": "2020-08-30 18:06:52"
//  },
//  "msg": null
//}
public class JoinBrandInfoResultBean extends BaseResultBean<JoinBrandInfoResultBean.JoinBrandInfoItem>implements HomeJoinItemType
{
    @NotNull
    @Override
    public Pair<Integer, Integer> getJoinItemType() {
        return HomeJoinListAdapterKt.JOIN_TYPE_INVITESHOP;
    }

    public class JoinBrandInfoItem implements Serializable
    {
        public String id;
        public String title;
        public String bannerUrl;
        public String topTitle;
        public String topUrl;
        public String bgl;
        public String fkl;
        public String introTitle;
        public String introOneTitle;
        public String introOneDesc;
        public String intrOnePic;
        public String introTwoTitle;
        public String introTwoDesc;
        public String introTwoPic;
        public String modifyTime;
    }
}
