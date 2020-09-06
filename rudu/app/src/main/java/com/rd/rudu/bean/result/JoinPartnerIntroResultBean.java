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
//    "title": "城市合伙人加盟",
//    "mainTitle": "CITY PARTNER",
//    "subTitle": "诚招城市合伙人",
//    "bannerUrl": "https://join-1302533250.cos.ap-guangzhou.myqcloud.com/join_partner/437e1055-8d25-4af1-8fa7-6c5c1f98797f.png",
//    "topUrl": "https://join-1302533250.cos.ap-guangzhou.myqcloud.com/join_partner/6180223a-e3ae-41b5-a7d6-ec4f400c33e7.png",
//    "introTitle": "合伙人报名",
//    "introDesc": "城市合伙人是誉都商城的合作方式，为广大的装企会员提供更多的增值服务。 如果您有兴趣，请填写以下信息，期待您的加入！\n                            ",
//    "modifyTime": "2020-08-31 12:59:49"
//  },
//  "msg": null
//}
public class JoinPartnerIntroResultBean extends BaseResultBean<JoinPartnerIntroResultBean.JoinPartnerIntro> implements HomeJoinItemType
{
    @NotNull
    @Override
    public Pair<Integer, Integer> getJoinItemType()
    {
        return HomeJoinListAdapterKt.JOIN_TYPE_JOINPARTNER;
    }

    public class JoinPartnerIntro implements Serializable
    {
        public String id;
        public String title;
        public String mainTitle;
        public String subTitle;
        public String bannerUrl;
        public String topUrl;
        public String introTitle;
        public String modifyTime;
        public String introDesc;
    }
}
