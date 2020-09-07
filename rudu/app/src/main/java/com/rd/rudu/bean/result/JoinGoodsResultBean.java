package com.rd.rudu.bean.result;

import com.rd.rudu.ui.adapter.HomeJoinItemType;
import com.rd.rudu.ui.adapter.HomeJoinListAdapterKt;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

import kotlin.Pair;

public class JoinGoodsResultBean extends BaseResultBean<List<JoinGoodsResultBean.JoinGoodsResultItem>> implements HomeJoinItemType {
    @NotNull
    @Override
    public Pair<Integer, Integer> getJoinItemType()
    {
        return HomeJoinListAdapterKt.JOIN_TYPE_HaoHuoTuiJianHeader;
    }

    public class JoinGoodsResultItem implements Serializable,HomeJoinItemType
    {
        @NotNull
        @Override
        public Pair<Integer, Integer> getJoinItemType()
        {
            return HomeJoinListAdapterKt.JOIN_TYPE_HaoHuoTuiJianItem;
        }

        public String id;
        public String itemId;
        public String title;
        public String detailUrl;
        public String imageUrl;
        public String price;
        public String origin;
        public String updateTime;
        public String modifyTime;
    }
}
