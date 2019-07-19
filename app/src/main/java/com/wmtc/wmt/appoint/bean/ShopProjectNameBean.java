package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

public class ShopProjectNameBean extends BaseBean {

    /**
     * result : ok
     * data : [{"dictProjectId":118,"dictProjectName":"黄金换肤"},{"dictProjectId":119,"dictProjectName":"雪花焕肤"},{"dictProjectId":127,"dictProjectName":"巧克力焕肤"},{"dictProjectId":128,"dictProjectName":"灵芝焕肤"},{"dictProjectId":129,"dictProjectName":"红酒焕肤"},{"dictProjectId":130,"dictProjectName":"马卡龙焕肤"},{"dictProjectId":145,"dictProjectName":"木乃伊焕肤"},{"dictProjectId":146,"dictProjectName":"叶绿素焕肤"},{"dictProjectId":147,"dictProjectName":"海藻焕肤"}]
     * message : 操作成功
     * curson : null
     * erros : null
     */

    public List<DataBean> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * dictProjectId : 118
         * dictProjectName : 黄金换肤
         */

        public String dictProjectId;
        public String dictProjectName;
        public boolean isSel = false;

        public String getDictProjectId() {
            return dictProjectId;
        }

        public void setDictProjectId(String dictProjectId) {
            this.dictProjectId = dictProjectId;
        }

        public String getDictProjectName() {
            return dictProjectName;
        }

        public void setDictProjectName(String dictProjectName) {
            this.dictProjectName = dictProjectName;
        }
    }
}
