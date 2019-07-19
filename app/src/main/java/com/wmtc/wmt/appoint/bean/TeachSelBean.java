package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/23.
 * com.wmtc.wmt.appoint.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TeachSelBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 317981261425541120
         * shopId : 308624582166708224
         * shopName : 刘杰的小铺1111
         * technicianName : www
         * notDo :
         * toDay : 4月
         * avatar : technician/317981261425541120/avatar35cdbdef-be50-4710-bf31-ac113e7463d2.png
         * title : www
         * goodAt : www
         * workTime : 2019-01-01
         * restDay : 星期一
         * createBy : 308624582166708224
         * createTime : 2019-03-28T03:04:01.000+0000
         * updateBy : 308624582166708224
         */

        public long id;
        public long shopId;
        public String shopName;
        public String technicianName;
        public String notDo;
        public String toDay;
        public String avatar;
        public String title;
        public String goodAt;
        public String workTime;
        public String restDay;
        public long createBy;
        public String createTime;
        public long updateBy;
        public boolean isSel = false;
    }
}
