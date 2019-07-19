package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/23.
 * com.wmtc.wmt.appoint.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TimeSelBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * times : ["14:50","15:00","15:10","15:20","15:30","15:40","15:50","16:00","16:10","16:20","16:30","16:40","16:50","17:00","17:10","17:20","17:30","17:40","17:50","18:00","18:10","18:20","18:30","18:40","18:50","19:00","19:10"]
         * week : 星期四
         * days : 2019-05-23
         * alias : 今天
         * timesValues : [[":50"],[":00",":10",":20",":30",":40",":50"],[":00",":10",":20",":30",":40",":50"],[":00",":10",":20",":30",":40",":50"],[":00",":10",":20",":30",":40",":50"],[":00",":10"]]
         * timesKeys : ["14","15","16","17","18","19"]
         * daysFix : 05-23
         */

        public String week;
        public String days;
        public String alias;
        public String daysFix;
        public List<String> times;
        public List<List<String>> timesValues;
        public List<String> timesKeys;
    }
}
