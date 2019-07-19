package com.wmtc.wmt.message.bean;

import com.google.gson.annotations.SerializedName;
import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.message.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MessageBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * messageId : 334704801969340416
         * messageTitle : 测试
         * messageContent : hello,nihao
         * createTime : 2019-05-13 14:37:23
         * createName : 系统管理员（test）
         */
        public String urlParam;
        public String urlName;
        public String messageId;
        public String messageTitle;
        public String messageContent;
        public String createTime;
        public String createName;
        public String chatId;
    }
}
