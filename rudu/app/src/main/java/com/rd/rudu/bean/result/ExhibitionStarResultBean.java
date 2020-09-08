package com.rd.rudu.bean.result;

import java.util.List;

public class ExhibitionStarResultBean extends BaseResultBean<List<ExhibitionStarResultBean.ExhibitionStarProduct>>
{

    public class ExhibitionStarProduct
    {
        public String id;
        public String exId;
        public String name;
        public String picUrl;
        public String desc;
        public String modifyTime;
    }
}
