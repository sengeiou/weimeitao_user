package com.wmtc.wmt.forum.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/3.
 * com.wmtc.wmt.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumCommentBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * commentaryId : 331119424851410944
         * judgeContent : 大结局
         * userId : 328488790869934080
         * name : 袁鹏
         * createTime : 2019-5-03 05:10:23
         * dianzanNum : 0
         * privateStatus : 1
         * avatar : avatar/201904286676f001-ad6e-4f00-90e0-2bdceaca7dca-photo.jpg
         * sublist : []
         */

        public String commentaryId;
        public String judgeContent;
        public String userId;
        public String name;
        public String createTime;
        public String dianzanNum;
        public String privateStatus;
        public String avatar;
        public List<?> sublist;
    }
}
