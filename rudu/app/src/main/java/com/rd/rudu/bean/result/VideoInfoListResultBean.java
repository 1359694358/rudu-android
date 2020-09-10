package com.rd.rudu.bean.result;

import java.util.List;

//资讯列表
public class VideoInfoListResultBean extends BaseResultBean<List<VideoInfoListResultBean.NewsInfoItem>>
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
        public String picUrl;
        public String videoUrl;
        public String videoName;
        public String picName;
        public String modifyTime;
    }
}
