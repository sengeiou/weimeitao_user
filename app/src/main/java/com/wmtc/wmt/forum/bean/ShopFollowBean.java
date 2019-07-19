package com.wmtc.wmt.forum.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/10.
 * com.wmtc.wmt.forum.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopFollowBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * attentionId : 329667622293471232
         * shopName : 刘杰game好的4r
         * shopId : 308624582166708224
         * shopAddress : 敦化路街道连云港路17号青房·财富地带
         * urlList : ["shopbanner/fa174f57b4a44a6cd7fb7b9fd13080fa.png","shopbanner/2dfb4a54591b648b766aec717f55e962.png","shopbanner/da542af327bcab8933c5003b24ff60d1.png","shopbanner/ea1eac8f565b00d8e70ff8b414e037a1.png"]
         */

        public String attentionId;
        public String shopName;
        public String shopId;
        public String shopAddress;
        public List<String> urlList;
    }
}
