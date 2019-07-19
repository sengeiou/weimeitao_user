package com.wmtc.wmt.appoint.event;

import com.wmtc.wmt.appoint.bean.CanCouponBean;

/**
 * Created by Obl on 2019/5/24.
 * com.wmtc.wmt.appoint.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class SelHbEvent {
    public CanCouponBean.DataBean.ListBean listBean;
    public SelHbEvent(CanCouponBean.DataBean.ListBean listBean) {
        this.listBean = listBean;
    }
}
