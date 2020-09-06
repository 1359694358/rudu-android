package com.rd.rudu.bean.result;

import java.util.List;

public class JoinMerchantsIntroResultBean extends BaseResultBean<List<JoinMerchantsIntroResultBean.JoinMerchantsIntroItem>>
{

    public class JoinMerchantsIntroItem
    {
        public String id;
        public String title;
        public String desc;
        public String modifyTime;
    }
}
