package com.wmtc.wmt.forum.adapter;

public class BackJidJmoney {

    private String jid;
    private String jmoney;
    private String type;

    public BackJidJmoney(String type, String jid, String jmoney) {
        this.type = type;
        this.jid = jid;
        this.jmoney = jmoney;
    }

    public String Backdate() {
        return jid + "," + jmoney + "," + type;
    }
}
