package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

public class CouponDialogbean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * couponName : 蔡超新店-5元券
         * scopeName : 蔡超新店
         * minPayAmount : 200
         * discountedWay : 1
         * discountedAmount : 100
         * startTime : 2019-04-27
         * endTime : 2019-05-11
         */

        public String couponId;
        public String ownerId;
        public String couponName;
        public String startTime;
        public String endTime;
        public String scopeName;
        public String discountedWay;
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
