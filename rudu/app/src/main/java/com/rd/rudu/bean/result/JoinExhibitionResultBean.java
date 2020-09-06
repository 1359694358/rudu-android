package com.rd.rudu.bean.result;

import com.rd.rudu.ui.adapter.HomeJoinItemType;
import com.rd.rudu.ui.adapter.HomeJoinListAdapterKt;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

import kotlin.Pair;

public class JoinExhibitionResultBean extends BaseResultBean<List<JoinExhibitionResultBean.JoinExhibitionResult>> implements HomeJoinItemType {
    @NotNull
    @Override
    public Pair<Integer, Integer> getJoinItemType() {
        return HomeJoinListAdapterKt.JOIN_TYPE_ZHANHUI_HEADER;
    }

    public class JoinExhibitionResult implements HomeJoinItemType, Serializable
    {
        @NotNull
        @Override
        public Pair<Integer, Integer> getJoinItemType() {
            return HomeJoinListAdapterKt.JOIN_TYPE_ZHANHUI_ITEM;
        }
        public String id;
        public String title;
        public String bannerUrl;
        public String exDate;
        public String topUrl;
        public String brand;
        public String contactPhone;
        public String contactMail;
        public String contactAddress;
        public String modifyTime;
    }
}
