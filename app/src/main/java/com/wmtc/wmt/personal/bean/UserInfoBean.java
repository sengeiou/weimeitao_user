package com.wmtc.wmt.personal.bean;

import com.wmtc.wmt.base.BaseBean;

public class UserInfoBean extends BaseBean {

    /**
     * data : {"id":"305382074368393216","avatar":"avatar/20190327d2b7bd3e-05ad-446c-9c32-6934ce9e07d5-photo.jpg","name":"吃，喝，玩","birthday":"1983-01-01","sex":"1","loginCityName":"合肥市","userFuzhiId":"198","userFuzhiName":"油性皮肤","introduce":"在线客服服务质","deliveryAddress":"山东省青岛市北区连云港路","interestName":"吃，喝，玩","jiguangId":"171976fa8acf3f23edc","account":"17609027963"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * id : 305382074368393216
         * avatar : avatar/20190327d2b7bd3e-05ad-446c-9c32-6934ce9e07d5-photo.jpg
         * name : 吃，喝，玩
         * birthday : 1983-01-01
         * sex : 1
         * loginCityName : 合肥市
         * userFuzhiId : 198
         * userFuzhiName : 油性皮肤
         * introduce : 在线客服服务质
         * deliveryAddress : 山东省青岛市北区连云港路
         * interestName : 吃，喝，玩
         * jiguangId : 171976fa8acf3f23edc
         * account : 17609027963
         */

        public String id;
        public String avatar;
        public String name;
        public String birthday;
        public String sex;
        public String loginCityName;
        public String userFuzhiId;
        public String userFuzhiName;
        public String introduce;
        public String deliveryAddress;
        public String interestName;
        public String jiguangId;
        public String account;
        public String contactTel;
    }
}
