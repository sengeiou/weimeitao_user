package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

/**
 * Created by Obl on 2019/5/24.
 * com.wmtc.wmt.appoint.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ShopHbBean extends BaseBean {

    /**
     * data : {"couponId":"338698274846277632","ownerId":"327499832975425536","couponName":"红包2","discountedAmount":"217","minPayAmount":"0","couponStartTime":"2019-05-24","couponEndTime":"2019-07-13","titleName":"刘杰game好的4r","onlineType":"0","dayNum":"50"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * couponId : 338698274846277632
         * ownerId : 327499832975425536
         * couponName : 红包2
         * discountedAmount : 217
         * minPayAmount : 0
         * couponStartTime : 2019-05-24
         * couponEndTime : 2019-07-13
         * titleName : 刘杰game好的4r
         * onlineType : 0
         * dayNum : 50
         */

        public String couponId;
        public String ownerId;
        public String couponName;
        public String discountedAmount;
        public String minPayAmount;
        public String couponStartTime;
        public String couponEndTime;
        public String titleName;
        public String onlineType;
        public String dayNum;
    }
}
