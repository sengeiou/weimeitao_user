package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

/**
 * Created by Obl on 2019/5/21.
 * com.wmtc.wmt.appoint.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopCouponBean extends BaseBean {

    /**
     * data : {"couponName":"红包1","couponAmount":"1546","minPayAmount":"0","scopeName":"店铺线上线下专属红包","scopeKey":"1","scopeId":"308624582166708224","shopOrProjectName":"刘杰game好的4r","couponStartTime":"2019-05-21 10:23:31","couponEndTime":"2019-05-31 10:23:31"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * couponName : 红包1
         * couponAmount : 1546
         * minPayAmount : 0
         * scopeName : 店铺线上线下专属红包
         * scopeKey : 1
         * scopeId : 308624582166708224
         * shopOrProjectName : 刘杰game好的4r
         * couponStartTime : 2019-05-21 10:23:31
         * couponEndTime : 2019-05-31 10:23:31
         */

        public String couponName;
        public String couponAmount;
        public String minPayAmount;
        public String scopeName;
        public String scopeKey;
        public String scopeId;
        public String shopOrProjectName;
        public String couponStartTime;
        public String couponEndTime;
    }
}
