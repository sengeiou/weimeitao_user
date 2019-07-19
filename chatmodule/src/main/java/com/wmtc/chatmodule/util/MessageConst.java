package com.wmtc.chatmodule.util;

import android.support.annotation.StringDef;

/**
 * Created by Obl on 2017/10/17.
 * com.ilanchuang.xiaoi.base
 */

public class MessageConst {
    public static final String TYPE_LEFT = "1";
    public static final String TYPE_RIGHT = "2";

    @StringDef({TYPE_LEFT, TYPE_RIGHT})
    public @interface Type {
    }
}
