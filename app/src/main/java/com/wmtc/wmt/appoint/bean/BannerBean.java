package com.wmtc.wmt.appoint.bean;

import java.util.List;

public class BannerBean {

    /**
     * result : ok
     * data : [{"path":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/banner/3dbc77ac-0ea1-4c23-b466-ae6e97868b04t.jpg","type":"1","sequence":2,"linkType":"0","linkUrl":""},{"path":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/banner/ea62743e-3148-4e48-8d84-46efd4f43150t.jpg","type":"1","sequence":3,"linkType":"0","linkUrl":""},{"path":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/banner/ee88081e-3778-4b85-89a6-532cb4cc2600wm.png","type":"1","sequence":5,"linkType":"0","linkUrl":"1asdasd"}]
     * message : 操作成功
     * curson : null
     * erros : null
     */

    private String result;
    private String message;
    private String curson;
    private String erros;
    private List<DataBean> data;

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

    public String getCurson() {
        return curson;
    }

    public void setCurson(String curson) {
        this.curson = curson;
    }

    public String getErros() {
        return erros;
    }

    public void setErros(String erros) {
        this.erros = erros;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * path : http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/banner/3dbc77ac-0ea1-4c23-b466-ae6e97868b04t.jpg
         * type : 1
         * sequence : 2
         * linkType : 0
         * linkUrl :
         */

        private String path;
        private String type;
        private int sequence;
        private String linkType;
        private String linkUrl;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public String getLinkType() {
            return linkType;
        }

        public void setLinkType(String linkType) {
            this.linkType = linkType;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }
    }
}
