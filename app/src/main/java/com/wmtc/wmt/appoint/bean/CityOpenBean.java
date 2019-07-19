package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

/**
 * Created by Obl on 2019/7/10.
 * com.wmtc.wmt.appoint.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CityOpenBean extends BaseBean {

    /**
     * data : {"id":7,"city":"string","like":1,"isOpen":0,"createBy":1,"createTime":"2019-07-10T07:29:29.299+0000","updateTime":"2019-07-10T07:29:29.299+0000","status":1}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * id : 7
         * city : string
         * like : 1
         * isOpen : 0
         * createBy : 1
         * createTime : 2019-07-10T07:29:29.299+0000
         * updateTime : 2019-07-10T07:29:29.299+0000
         * status : 1
         */

        public int id;
        public String city;
        public int like;
        public int isOpen;
        public int createBy;
        public String createTime;
        public String updateTime;
        public int status;
    }
}
