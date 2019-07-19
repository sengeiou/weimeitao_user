package com.wmtc.wmt.personal.event;

public class WxLoginEvent {
    private String code = "";
    private String msg = "";

    public WxLoginEvent(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String backCode() {
        return code;
    }

    public String backMsg() {
        return msg;
    }
}
