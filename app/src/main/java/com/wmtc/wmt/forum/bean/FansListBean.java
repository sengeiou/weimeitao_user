package com.wmtc.wmt.forum.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/10.
 * com.wmtc.wmt.mvp.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class FansListBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * userId : 328488790869934080
         * name : 袁鹏测试
         * userSex : 1
         * birthday : 1983-1-01 12:00:00
         * fuzhi : 干性皮肤
         * loginCityName : 青岛市
         * avatar : avatar/20190505b7dc68ee-bcc3-4018-94db-f0e9e915fd20-1557020598560.png
         * attntionStatus : 1
         * attentionUserId : 328488790869934080
         */

        public String userId;
        public String name;
        public String userSex;
        public String birthday;
        public String fuzhi;
        public String loginCityName;
        public String avatar;
        public String attntionStatus = "1";
        public String attentionUserId;
    }

}
