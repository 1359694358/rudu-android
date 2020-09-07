package com.rd.rudu.bean.result;

import java.util.List;

public class JoinIntroEliteResultBean extends BaseResultBean<List<JoinIntroEliteResultBean.JoinIntroEliteItem>>
{
    public class JoinIntroEliteItem
    {
        public String id;
        public String name;
        public String position;
        public String picUrl;
        public String sort;
        public String modifyTime;
    }
}
