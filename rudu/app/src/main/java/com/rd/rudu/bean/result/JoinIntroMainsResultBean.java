package com.rd.rudu.bean.result;

import java.util.List;

public class JoinIntroMainsResultBean extends BaseResultBean<List<JoinIntroMainsResultBean.JoinIntroMainsItem>>
{
    public class JoinIntroMainsItem
    {
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
