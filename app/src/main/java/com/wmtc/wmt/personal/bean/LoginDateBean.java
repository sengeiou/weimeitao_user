package com.wmtc.wmt.personal.bean;

import com.wmtc.wmt.base.BaseBean;

public class LoginDateBean extends BaseBean {

    /**
     * data : {"birthday":"","sex":"","userId":"332556967430062080","account":"17609027963","token":"6fa075ad7158382b859ef5cbe219e229"}
     */

    public DataBean data;

    public static class DataBean {

        public String name;
        public String avatar;
        public String birthday;
        public String sex;
        public String account;
        public String userId;
        public String token;
    }
}
