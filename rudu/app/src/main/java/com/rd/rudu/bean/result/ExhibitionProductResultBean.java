package com.rd.rudu.bean.result;

import java.util.List;

public class ExhibitionProductResultBean extends BaseResultBean<List<ExhibitionProductResultBean.ExhibitionProduct>>
{

    public class ExhibitionProduct
    {
        public String id;
        public String exId;
        public String name;
        public String picUrl;
        public String desc;
        public String modifyTime;
    }
}
