package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

public class WenTiBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 402
         * num : 1
         * pid : 257
         * name : 皮肤干燥
         * list : []
         */

        public String id;
        public String num;
        public String pid;
        public String name;
        public List<?> list;
    }
}
