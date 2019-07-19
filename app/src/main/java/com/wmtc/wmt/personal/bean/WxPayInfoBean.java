package com.wmtc.wmt.personal.bean;

import com.google.gson.annotations.SerializedName;
import com.wmtc.wmt.base.BaseBean;

/**
 * Created by Obl on 2019/4/23.
 * com.wmtc.wmt.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class WxPayInfoBean extends BaseBean {

    /**
     * data : {"package":"Sign=WXPay","appid":"wxb3ced52d70985d54","sign":"1CC9DB6220B3FEF6B469F029471F1CC4","partnerid":"1527963941","prepayid":"wx231831398809702dbd84df580981557772","noncestr":"Fv51BCRQQGJ5tPfz","timestamp":"1556015499"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * package : Sign=WXPay
         * appid : wxb3ced52d70985d54
         * sign : 1CC9DB6220B3FEF6B469F029471F1CC4
         * partnerid : 1527963941
         * prepayid : wx231831398809702dbd84df580981557772
         * noncestr : Fv51BCRQQGJ5tPfz
         * timestamp : 1556015499
         */

        @SerializedName("package")
        public String packageX;
        public String appid;
        public String sign;
        public String partnerid;
        public String prepayid;
        public String noncestr;
        public String timestamp;
    }
}
