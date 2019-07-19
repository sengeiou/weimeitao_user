package com.wmtc.wmt.forum.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/10.
 * com.wmtc.wmt.forum.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MeForumCountBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * type : 1
         * count : 0
         */

        public String type;
        public String count;
    }
}
