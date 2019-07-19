package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

public class RefundBean extends BaseBean {

    /**
     * data : {"transaction_id":"4200000334201904268281990154","nonce_str":"OQDbUDaIrpaZ1vVq","out_refund_no":"9CD842B53D2F4531AC48953597F7D057","sign":"D0B52C76E9725F922D9452F11D2CE432","return_msg":"OK","mch_id":"1527963941","refund_id":"50000200072019042609304450034","cash_fee":"20","out_trade_no":"4112364936B64346B1384355AC3E6583","coupon_refund_fee":"0","refund_channel":"","appid":"wxb3ced52d70985d54","refund_fee":"20","total_fee":"20","result_code":"SUCCESS","coupon_refund_count":"0","cash_refund_fee":"20","return_code":"SUCCESS"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * transaction_id : 4200000334201904268281990154
         * nonce_str : OQDbUDaIrpaZ1vVq
         * out_refund_no : 9CD842B53D2F4531AC48953597F7D057
         * sign : D0B52C76E9725F922D9452F11D2CE432
         * return_msg : OK
         * mch_id : 1527963941
         * refund_id : 50000200072019042609304450034
         * cash_fee : 20
         * out_trade_no : 4112364936B64346B1384355AC3E6583
         * coupon_refund_fee : 0
         * refund_channel :
         * appid : wxb3ced52d70985d54
         * refund_fee : 20
         * total_fee : 20
         * result_code : SUCCESS
         * coupon_refund_count : 0
         * cash_refund_fee : 20
         * return_code : SUCCESS
         */

        public String transaction_id;
        public String nonce_str;
        public String out_refund_no;
        public String sign;
        public String return_msg;
        public String mch_id;
        public String refund_id;
        public String cash_fee;
        public String out_trade_no;
        public String coupon_refund_fee;
        public String refund_channel;
        public String appid;
        public String refund_fee;
        public String total_fee;
        public String result_code;
        public String coupon_refund_count;
        public String cash_refund_fee;
        public String return_code;
    }
}
