package com.wmtc.wmt.appoint.event;

import com.wmtc.wmt.appoint.pojo.RefundPojo;

/**
 * Created by Obl on 2019/6/26.
 * com.wmtc.wmt.appoint.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CancelOrderEvent {
    public RefundPojo mRefundPojo;
    public String type;

    public CancelOrderEvent(RefundPojo pojo, String type) {
        this.mRefundPojo = pojo;
        this.type = type;
    }
}
