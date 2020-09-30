package com.rd.rudu.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.app.utils.ToastUtil;
import com.google.android.app.utils.Utility;
import com.mediacloud.app.share.SocialShareControl;
import com.mediacloud.app.share.SocialShareInfo;
import com.mediacloud.app.share.model.ShareGridItem;
import com.mediacloud.app.share.utils.ShareGridDataUtil;
import com.mediacloud.app.share.view.SharePopGridWindow;
import com.rd.rudu.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.youzan.androidsdk.model.goods.GoodsShareModel;

/**
 * Created by hello-world on 2018/5/21.
 */
public class ShareGridPopUtils
{
    public static void show(final Activity context, final GoodsShareModel data, View root)
    {
        SharePopGridWindow sharePopWindow;
        final ShareGridDataUtil shareGridDataUtil= new ShareGridDataUtil();
        sharePopWindow = new SharePopGridWindow(context);
        sharePopWindow.setShareGirdListener((parent, view, position, id) -> {
            String label = shareGridDataUtil.BaseShareGridData.get(position).label;
            SocialShareInfo shareInfo=new SocialShareInfo(data.getLink(),data.getTitle(),data.getDesc(),data.getImgUrl());
            if(ShareGridDataUtil.CopyLink.equals(label))
            {
                Utility.copyText(data.getLink(),context);
                ToastUtil.show(context,"复制成功");
            }
            else
            {
                if(!SocialShareControl.isWeiXinInstall(context))
                {
                    ToastUtil.show(context,"您未安装微信");
                    return;
                }
                if(ShareGridDataUtil.WeiXinFriend.equals(label))
                    SocialShareControl.share(context, SHARE_MEDIA.WEIXIN,shareInfo);//直接分享到微信
                else if(ShareGridDataUtil.WeiXinCircle.equals(label))
                    SocialShareControl.share(context, SHARE_MEDIA.WEIXIN_CIRCLE,shareInfo);//直接分享到微信
            }
        });
        shareGridDataUtil.initBaseShareGridData(context);
        shareGridDataUtil.BaseShareGridData.clear();
        shareGridDataUtil.BaseShareGridData.add(new ShareGridItem(ShareGridDataUtil.WeiXinFriend, R.drawable.grid_weixin_friend, ShareGridDataUtil.WeiXinFriend));
        shareGridDataUtil.BaseShareGridData.add(new ShareGridItem(ShareGridDataUtil.WeiXinCircle, R.drawable.grid_weixin_circle, ShareGridDataUtil.WeiXinCircle));
        shareGridDataUtil.BaseShareGridData.add(new ShareGridItem(ShareGridDataUtil.CopyLink, R.drawable.grid_copy_link, ShareGridDataUtil.CopyLink));
        shareGridDataUtil.removeGridItem(ShareGridDataUtil.Comment);
        shareGridDataUtil.removeGridItem(ShareGridDataUtil.QQ);
        shareGridDataUtil.removeGridItem(ShareGridDataUtil.QQZone);
        shareGridDataUtil.removeGridItem(ShareGridDataUtil.SinaWeiBo);
        shareGridDataUtil.removeGridItem(ShareGridDataUtil.Collection);
        shareGridDataUtil.removeGridItem(ShareGridDataUtil.Likes);
        sharePopWindow.setShareGridAdaptorData(shareGridDataUtil.BaseShareGridData);
        sharePopWindow.show(root);
    }
}