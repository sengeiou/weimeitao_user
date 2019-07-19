package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

public class DictListBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 199
         * num : 1
         * pid : 198
         * name : 油性皮肤
         * list : []
         */

        public String id;
        public String num;
        public String pid;
        public String name;
        public List<?> list;
        public boolean isSel = false;
    }
}
