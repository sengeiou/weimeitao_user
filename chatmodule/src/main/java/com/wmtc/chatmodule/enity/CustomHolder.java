package com.wmtc.chatmodule.enity;

import android.support.annotation.LayoutRes;

import com.wmtc.chatmodule.base.MessageBaseHolder;
import com.wmtc.chatmodule.base.MessageBaseInfo;

/**
 * Created by Obl on 2019/7/5.
 * com.wmtc.chatmodule.enity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class CustomHolder<T extends MessageBaseHolder,K extends MessageBaseInfo> {
    public Class<T> holderClass;
    public @LayoutRes
    int layout;
    public  Class<K> msg;

    public CustomHolder(Class<T> holderClass, @LayoutRes int layout, Class<K> msg) {
        this.holderClass = holderClass;
        this.layout = layout;
        this.msg = msg;
    }
}
