package com.rd.rudu.bean.result;

import java.util.List;

public class ExhibitionSpecialResultBean extends BaseResultBean<List<ExhibitionSpecialResultBean.ExhibitionSpecialProduct>>
{

    public class ExhibitionSpecialProduct
    {
        public String id;
        public String exId;
        public String name;
        public String picUrl;
        public String linkUrl;
        public String modifyTime;
    }
}
