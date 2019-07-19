package com.wmtc.wmt.forum.event;

/**
 * Created by Obl on 2019/5/13.
 * com.wmtc.wmt.forum.event
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class TopicSelEvent {
    public String title;
    public String code;

    public TopicSelEvent(String title,String code) {
        this.title = title;
        this.code = code;
    }
}
