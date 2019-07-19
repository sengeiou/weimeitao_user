package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

public class CanCouponBean extends BaseBean {

    /**
     * data : {"can":[{"couponId":"329989987158196224","ownerId":"329989843385843712","couponName":"新人5元优惠券","discountedAmount":"500","minPayAmount":"100","couponStartTime":"2019-04-30","couponEndTime":"2019-05-10","onlineType":"1","dayNum":"8","couponType":"平台专属","couponTypeCode":"4"}],"cannot":[{"couponId":"329989986235449344","ownerId":"329989843385843712","couponName":"蔡超新店-5元券","discountedAmount":"100","minPayAmount":"200","couponStartTime":"2019-04-26","couponEndTime":"2019-05-10","titleName":"蔡超新店","onlineType":"1","dayNum":"9","couponType":"店铺专属","couponTypeCode":"2"}],"cannotNum":"1","canNum":"1"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * can : [{"couponId":"329989987158196224","ownerId":"329989843385843712","couponName":"新人5元优惠券","discountedAmount":"500","minPayAmount":"100","couponStartTime":"2019-04-30","couponEndTime":"2019-05-10","onlineType":"1","dayNum":"8","couponType":"平台专属","couponTypeCode":"4"}]
         * cannot : [{"couponId":"329989986235449344","ownerId":"329989843385843712","couponName":"蔡超新店-5元券","discountedAmount":"100","minPayAmount":"200","couponStartTime":"2019-04-26","couponEndTime":"2019-05-10","titleName":"蔡超新店","onlineType":"1","dayNum":"9","couponType":"店铺专属","couponTypeCode":"2"}]
         * cannotNum : 1
         * canNum : 1
         */

        public String cannotNum;
        public String canNum;
        public List<ListBean> can;
        public List<ListBean> cannot;

        public static class ListBean {
            /**
             * couponId : 329989987158196224
             * ownerId : 329989843385843712
             * couponName : 新人5元优惠券
             * discountedAmount : 500
             * minPayAmount : 100
             * couponStartTime : 2019-04-30
             * couponEndTime : 2019-05-10
             * onlineType : 1
             * dayNum : 8
             * couponType : 平台专属
             * couponTypeCode : 4
             */

            public boolean ischeck;
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
            public String couponType;
            public String couponTypeCode;
            public String shopId;
            public boolean isSel = false;
        }

    }
}
