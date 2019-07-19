package com.wmtc.wmt.personal.bean;

import com.wmtc.wmt.base.BaseBean;

public class CodeLoginBean extends BaseBean {

    /**
     * data : {"birthday":"1937-01-01","sex":"1","name":"斌斌大魔王王","avatar":"avatar/201904221c65336a-6ba7-4bc9-986c-b7916a0505d7-1555916934252.png","userId":"327100865305378816","contactTel":"17609027963","account":"17609027963","token":"af123d7159f9143ba0c4e83d93b0dd4b"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * birthday : 1937-01-01
         * sex : 1
         * name : 斌斌大魔王王
         * avatar : avatar/201904221c65336a-6ba7-4bc9-986c-b7916a0505d7-1555916934252.png
         * userId : 327100865305378816
         * contactTel : 17609027963
         * account : 17609027963
         * token : af123d7159f9143ba0c4e83d93b0dd4b
         */

        public String birthday;
        public String sex;
        public String name;
        public String avatar;
        public String userId;
        public String contactTel;
        public String account;
        public String token;
    }
}
