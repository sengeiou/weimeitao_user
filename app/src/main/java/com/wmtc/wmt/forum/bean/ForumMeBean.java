package com.wmtc.wmt.forum.bean;

import com.wmtc.wmt.base.BaseBean;

/**
 * Created by Obl on 2019/5/9.
 * com.wmtc.wmt.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumMeBean extends BaseBean {

    /**
     * data : {"attentionCount":"6","fensCount":"2","zanCount":"4","collectionCount":"2"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * attentionCount : 6
         * fensCount : 2
         * zanCount : 4
         * collectionCount : 2
         */

        public String attentionCount;
        public String fensCount;
        public String zanCount;
        public String collectionCount;
    }
}
