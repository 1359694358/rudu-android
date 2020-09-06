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
//    "title": "誉都简介",
//    "introTitle": "誉都商城",
//    "introDesc": "誉都商城专注建材家居，品质保证。",
//    "introPic": "https://join-1302533250.cos.ap-guangzhou.myqcloud.com/join_partner/437e1055-8d25-4af1-8fa7-6c5c1f98797f.png",
//    "topPic": "https://join-1302533250.cos.ap-guangzhou.myqcloud.com/join_partner/437e1055-8d25-4af1-8fa7-6c5c1f98797f.png",
//    "moduleAboutTitle": "关于誉都",
//    "moduleAboutDesc": "   誉都商城有限公司成立于2014年，专注“建材家居”十余载。 成立伊始在广州建立展柜制作厂，熟知展示家具的制作成本和生产工艺。2014年在深圳成立装修公司。",
//    "moduleAboutPic": "https://join-1302533250.cos.ap-guangzhou.myqcloud.com/join_partner/437e1055-8d25-4af1-8fa7-6c5c1f98797f.png",
//    "moduleContactTitle": "联系我们",
//    "moduleContactPhone": "188 0314 0558",
//    "moduleContactMail": "25688441@mail.com",
//    "moduleContactAddress": "西安市长安大道77号",
//    "modifyTime": "2020-08-31 15:55:41"
//  },
//  "msg": null
//}

public class JoinIntroInfoResultBean extends BaseResultBean<JoinIntroInfoResultBean.JoinIntroInfoListData> implements HomeJoinItemType {
    @NotNull
    @Override
    public Pair<Integer, Integer> getJoinItemType() {
        return HomeJoinListAdapterKt.JOIN_TYPE_INTRO;
    }

    public class JoinIntroInfoListData implements Serializable
    {
        public String id;
        public String title;
        public String introTitle;
        public String introDesc;
        public String introPic;
        public String topPic;
        public String moduleAboutTitle;
        public String moduleAboutDesc;
        public String moduleAboutPic;
        public String moduleContactTitle;
        public String moduleContactPhone;
        public String moduleContactMail;
        public String moduleContactAddress;
        public String modifyTime;
    }
}
