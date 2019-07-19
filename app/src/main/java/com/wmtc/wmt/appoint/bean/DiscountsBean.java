package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

public class DiscountsBean extends BaseBean {


    /**
     * data : {"num":"2","list":[{"couponId":null,"ownerId":"327100865305378816","couponName":"新人5元优惠券","discountedAmount":"500","minPayAmount":"100","couponStartTime":"2019-04-28","couponEndTime":"2019-05-08","onlineType":"1","dayNum":"10","couponType":"平台专属","couponTypeCode":"4"},{"couponId":null,"ownerId":"327100865305378816","couponName":"新人8折优惠券","discountedAmount":"0.8","minPayAmount":"9000","couponStartTime":"2019-04-28","couponEndTime":"2019-05-18","onlineType":"1","dayNum":"20","couponType":"平台专属","couponTypeCode":"4"}]}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * num : 2
         * list : [{"couponId":null,"ownerId":"327100865305378816","couponName":"新人5元优惠券","discountedAmount":"500","minPayAmount":"100","couponStartTime":"2019-04-28","couponEndTime":"2019-05-08","onlineType":"1","dayNum":"10","couponType":"平台专属","couponTypeCode":"4"},{"couponId":null,"ownerId":"327100865305378816","couponName":"新人8折优惠券","discountedAmount":"0.8","minPayAmount":"9000","couponStartTime":"2019-04-28","couponEndTime":"2019-05-18","onlineType":"1","dayNum":"20","couponType":"平台专属","couponTypeCode":"4"}]
         */

        public String num;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * couponId : null
             * ownerId : 327100865305378816
             * couponName : 新人5元优惠券
             * discountedAmount : 500
             * minPayAmount : 100
             * couponStartTime : 2019-04-28
             * couponEndTime : 2019-05-08
             * onlineType : 1
             * dayNum : 10
             * couponType : 平台专属
             * couponTypeCode : 4
             */

            public String couponId;
            public String ownerId;
            public String couponName;
            public String discountedAmount;
            public String minPayAmount;
            public String couponStartTime;
            public String couponEndTime;
            public String onlineType;
            public String dayNum;
            public String couponType;
            public String couponTypeCode;
            public String shopId;
            public String titleName;
        }
    }
}
