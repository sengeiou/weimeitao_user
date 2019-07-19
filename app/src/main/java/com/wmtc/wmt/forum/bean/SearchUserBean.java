package com.wmtc.wmt.forum.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/5.
 * com.wmtc.wmt.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SearchUserBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * uid : 331743027397656576
         * name : 九九妖妖
         * birthday : 1995-01-01 00:00:00.0
         * avatar : avatar/201905021b9650c0-9215-41ed-9255-2ea652880735-1556791020991.png
         * fuzhi : 中性皮肤
         */

        public String uid;
        public String name;
        public String birthday;
        public String avatar;
        public String fuzhi;
        public boolean isFollow = false;
    }
}
