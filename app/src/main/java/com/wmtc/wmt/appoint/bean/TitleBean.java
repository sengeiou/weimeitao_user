package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/16.
 * com.wmtc.wmt.appoint.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TitleBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 138
         * num : 0
         * pid : 0
         * name : 护肤千万种，适合才最好
         * list : []
         */

        public int id;
        public int num;
        public int pid;
        public String name;
        public List<Object> list;
    }
}
