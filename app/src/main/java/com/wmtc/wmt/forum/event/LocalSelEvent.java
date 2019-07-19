package com.wmtc.wmt.forum.event;

/**
 * Created by Obl on 2019/4/28.
 * com.wmtc.wmt.mvp.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LocalSelEvent {
    public String local;
    public String lat;
    public String lot;
    public String city;

    public LocalSelEvent(String local, String lat, String lot, String city) {
        this.local = local;
        this.lat = lat;
        this.lot = lot;
        this.city = city;
    }
}
