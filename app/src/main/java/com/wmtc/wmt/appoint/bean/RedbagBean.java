package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

public class RedbagBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * couponId : null
         * ownerId : 327100865305378816
         * couponName : 红包1
         * discountedAmount : 1630
         * minPayAmount : 0
         * couponStartTime : 2019-04-22
         * couponEndTime : 2019-05-02
         * titleName : 温蔓CBD111
         * onlineType : 2
         * dayNum : 9
         * couponType : 现金红包
         * couponTypeCode : 1
         * shopId : 322384919625990144
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
        public String couponType;
        public String couponTypeCode;
        public String shopId;
    }
}
