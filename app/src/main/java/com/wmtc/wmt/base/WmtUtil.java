package com.wmtc.wmt.base;

import android.app.Activity;
import android.os.Bundle;

import com.wmtc.wmt.BuildConfig;
import com.wmtc.wmt.appoint.activity.ProjectInfoActivity;
import com.wmtc.wmt.appoint.activity.ShopInfoActivity;
import com.wmtc.wmt.forum.activity.ForumDetailActivity;

import top.jplayer.baseprolibrary.ui.activity.WebViewActivity;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;

/**
 * Created by Obl on 2019/5/16.
 * com.wmtc.wmt.appoint.util
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class WmtUtil {

    public static WmtUtil mWmtUtil;

    public static WmtUtil init() {
        if (mWmtUtil == null) {
            mWmtUtil = new WmtUtil();
        }
        return mWmtUtil;
    }

    public static String getStatus(String orderStatus) {
        if ("1".equals(orderStatus)) {
            return "待支付";//(待支付（已下单）)
        } else if ("2".equals(orderStatus)) {
            return "未支付";//(10分钟超时,定时任务取消)
        } else if ("3".equals(orderStatus)) {
            return "待接单";//(商家确认中)
        } else if ("4".equals(orderStatus)) {
            return "拒接单";//(商家拒绝接单)
        } else if ("5".equals(orderStatus)) {
            return "待到店";//(待到店（商家已接单）)
        } else if ("6".equals(orderStatus)) {
            return "已完成";//(已支付尾款)
        } else if ("7".equals(orderStatus)) {
            return "已取消";//(用户取消)
        } else if ("8".equals(orderStatus)) {
            return "已违约";//(用户超时违约)
        } else if ("9".equals(orderStatus)) {
            return "已退款";//(商家未接单，定时任务取消)
        } else if ("10".equals(orderStatus)) {
            return "已取消";//(用户超时未到店，商家取消)
        } else if ("11".equals(orderStatus)) {
            return "已取消";//(用户未付预约金，主动取消)
        } else if ("12".equals(orderStatus)) {
            return "退款中";//(申请售后)
        } else if ("13".equals(orderStatus)) {
            return "退款成功";//(申请售后•商家同意退款)
        } else if ("14".equals(orderStatus)) {
            return "退款成功";//(申请售后•商家拒绝退款)
        } else if ("15".equals(orderStatus)) {
            return "重新激活";//(售后•重新激活)
        }
        return "";
    }

    public static String wmShare = BuildConfig.DEBUG ?
            "http://yinsi.weimeitao.com/h5wmt/ceshi/#/?type=0&fromId=" :
            "http://yinsi.weimeitao.com/h5wmt/wmtshare/#/?type=1&fromId=";


    public void bannerType(Activity activity, String linkType, String linkUrl) {
        if ("0".equals(linkType)) {
            Bundle bundle = new Bundle();
            bundle.putString("url", linkUrl);
            ActivityUtils.init().start(activity, WebViewActivity.class, bundle);
        } else if ("1,2,3,4,6".contains(linkType)) {
            Bundle bundle = new Bundle();
            bundle.putString("id", linkUrl);
            ActivityUtils.init().start(activity, ForumDetailActivity.class, "浏览", bundle);
        } else if ("5".equals(linkType)) {
            Bundle bundle = new Bundle();
            bundle.putString("id", linkUrl);
            ActivityUtils.init().start(activity, ProjectInfoActivity.class, "", bundle);
        } else if ("7".equals(linkType)) {
            Bundle bundle = new Bundle();
            bundle.putString("id", linkUrl);
            bundle.putParcelable("pojo", new Bundle());
            ActivityUtils.init().start(activity, ShopInfoActivity.class, "", bundle);
        } else {
            LogUtil.e("未定义");
        }
    }


    public void bannerType(String type) {
        /*
         * <option value="0">外部链接</option>
          <option value="1">日记</option>
          <option value="2">心得</option>
          <option value="3">合辑</option>
          <option value="4">干货</option>
          <option value="5">项目</option>
          <option value="6">话题</option>
          <option value="7">店铺</option>
          <option value="8">活动页</option>
          <option value="9">领券</option>
          <option value="10">领红包</option>
          <option value="11">邀请有礼</option>
          <option value="12">没有链接</option>
        * */
    }
}
