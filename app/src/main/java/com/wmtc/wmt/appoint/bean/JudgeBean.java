package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

public class JudgeBean extends BaseBean {


    /**
     * data : {"total_page":1,"count":2,"resultList":[{"id":"318340203783651328","orderId":"318339880956461056","projectId":"308641983251873792","isAnnoy":"0","judgeContent":"不好呢","createTime":"2019-03-29","avatar":"avatar/20190327d2b7bd3e-05ad-446c-9c32-6934ce9e07d5-photo.jpg","name":"蔡超测试（勿动）","picUrl":"banner/1/3.png","projectName":"三千痴缠~皮肤护理","projectAllPrice":"1.00"},{"id":"317410040581455872","orderId":"317343904611434496","projectId":"111624582166708224","isAnnoy":"0","judgeContent":"不好呀","createTime":"2019-03-26","avatar":"avatar/2019032728830df0-67cd-439f-b108-c4b281a11981-photo.jpg","name":"123","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","projectAllPrice":"1.00"}]}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * total_page : 1
         * count : 2
         * resultList : [{"id":"318340203783651328","orderId":"318339880956461056","projectId":"308641983251873792","isAnnoy":"0","judgeContent":"不好呢","createTime":"2019-03-29","avatar":"avatar/20190327d2b7bd3e-05ad-446c-9c32-6934ce9e07d5-photo.jpg","name":"蔡超测试（勿动）","picUrl":"banner/1/3.png","projectName":"三千痴缠~皮肤护理","projectAllPrice":"1.00"},{"id":"317410040581455872","orderId":"317343904611434496","projectId":"111624582166708224","isAnnoy":"0","judgeContent":"不好呀","createTime":"2019-03-26","avatar":"avatar/2019032728830df0-67cd-439f-b108-c4b281a11981-photo.jpg","name":"123","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","projectAllPrice":"1.00"}]
         */

        public int total_page;
        public int count;
        public List<ResultListBean> resultList;

        public static class ResultListBean {
            /**
             * id : 318340203783651328
             * orderId : 318339880956461056
             * projectId : 308641983251873792
             * isAnnoy : 0
             * judgeContent : 不好呢
             * createTime : 2019-03-29
             * avatar : avatar/20190327d2b7bd3e-05ad-446c-9c32-6934ce9e07d5-photo.jpg
             * name : 蔡超测试（勿动）
             * picUrl : banner/1/3.png
             * projectName : 三千痴缠~皮肤护理
             * projectAllPrice : 1.00
             */

            public String id;
            public String orderId;
            public String projectId;
            public String isAnnoy;
            public String judgeContent;
            public String createTime;
            public String avatar;
            public String name;
            public String picUrl;
            public String projectName;
            public String projectAllPrice;
        }
    }
}
