package com.wmtc.wmt.personal.bean;

/**
 * Created by Administrator on 2019/2/20.
 */
public class UpdateBean {

    /**
     * result : ok
     * data : {"description":"1","isForce":"1","time":"2019-02-20T14:17:31","versionName":"1","versionCode":"1","url":"1"}
     * message : 操作成功
     * curson : null
     * erros : null
     */

    private String result;
    private DataBean data;
    private String message;
    private String curson;
    private String erros;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getCurson() {
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

    public static class DataBean {
        /**
         * description : 1
         * isForce : 1
         * time : 2019-02-20T14:17:31
         * "url":"1"
         * versionName : 1
         * versionCode : 1
         */

        private String description;
        private String isForce;
        private String time;
        private String versionName;
        private String versionCode;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIsForce() {
            return isForce;
        }

        public void setIsForce(String isForce) {
            this.isForce = isForce;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }
    }
}
