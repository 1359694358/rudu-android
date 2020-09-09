package com.rd.rudu.bean.result;

import java.util.List;

//资讯列表
public class NewsInfoListResultBean extends BaseResultBean<List<NewsInfoListResultBean.NewsInfoItem>>
{
    public int total_size;
    public int total_page;
    public int current_page;

    public boolean haveMore()
    {
        return total_page!=current_page;
    }
    public class NewsInfoItem
    {
        public String id;
        public String title;
        public String author;
        public String releaseDate;
        public int showPicCount;
        public String picUrlOne;
        public String picUrlTwo;
        public String picUrlThree;
        public String modifyTime;
        public String newsInfoDetails;
    }
}
