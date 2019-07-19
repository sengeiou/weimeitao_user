package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/28.
 * com.wmtc.wmt.appoint.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProBkBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 374
         * num : 1
         * pid : 373
         * name : 深层清洁
         * list : [{"id":349,"num":1,"pid":346,"name":"黄金焕肤","list":[]},{"id":352,"num":4,"pid":346,"name":"灵芝焕肤","list":[]},{"id":355,"num":7,"pid":346,"name":"超声波皮铲机清洁","list":[]},{"id":360,"num":12,"pid":346,"name":"小气泡清洁","list":[]}]
         */

        public int id;
        public int num;
        public int pid;
        public String name;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * id : 349
             * num : 1
             * pid : 346
             * name : 黄金焕肤
             * list : []
             */

            public int id;
            public int num;
            public int pid;
            public String name;
            public List<?> list;
            public boolean isSel = false;
        }
    }
}
