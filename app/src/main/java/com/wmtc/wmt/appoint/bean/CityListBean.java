package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/7/10.
 * com.wmtc.wmt.appoint.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CityListBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 1
         * city : 青岛市
         * like : 10019
         * isOpen : 1
         * createBy : 313720770029158400
         * createTime : 2019-03-16T08:54:20.000+0000
         * updateTime : 2019-03-16T09:40:38.000+0000
         */

        public int id;
        public String city;
        public int like;
        public int isOpen;
        public long createBy;
        public String createTime;
        public String updateTime;
    }
}
