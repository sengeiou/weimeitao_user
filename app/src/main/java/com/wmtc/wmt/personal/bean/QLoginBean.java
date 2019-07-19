package com.wmtc.wmt.personal.bean;

import com.wmtc.wmt.base.BaseBean;

public class QLoginBean extends BaseBean {

    /**
     * data : {"birthday":"","auth_id":"44DDE00B2C2F866A2BEB7C34C229EC1E","sex":"null","channel":"QQ","userId":"306117486137311232","token":"e993bfcfbf4d85cecc1f3f2e93c6d172"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * birthday :
         * auth_id : 44DDE00B2C2F866A2BEB7C34C229EC1E
         * sex : null
         * channel : QQ
         * userId : 306117486137311232
         * token : e993bfcfbf4d85cecc1f3f2e93c6d172
         */

        public String birthday;
        public String auth_id;
        public String sex;
        public String name;
        public String channel;
        public String avatar;
        public String userId;
        public String token;
        public String account;
        public String unionId;
    }
}
