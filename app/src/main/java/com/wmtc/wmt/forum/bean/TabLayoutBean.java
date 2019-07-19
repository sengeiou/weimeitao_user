package com.wmtc.wmt.forum.bean;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by Obl on 2019/5/9.
 * com.wmtc.wmt.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TabLayoutBean implements CustomTabEntity {
    public String title;

    public TabLayoutBean(String title) {
        this.title = title;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }

}
