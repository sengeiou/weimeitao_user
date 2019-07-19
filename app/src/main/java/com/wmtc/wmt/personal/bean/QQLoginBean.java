package com.wmtc.wmt.personal.bean;

/**
 * Created by Administrator on 2019/2/23.
 */
public class QQLoginBean {

    /**
     * result : ok
     * data : {"birthday":"111","auth_id":"44DDE00B2C2F866A2BEB7C34C229EC1E","sex":"111","channel":"QQ","name":"11","avatar":"111","userId":"306117486137311232","token":"641d8218a5aa819b42d64eda25e68b67"}
     * message : 操作成功
     * curson :
     * erros :
     */

    private String result;
    private DataBean data;
    private String message;
    private String curson;
    private String erros;

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

    public static class DataBean {
        /**
         * birthday : 111
         * auth_id : 44DDE00B2C2F866A2BEB7C34C229EC1E
         * sex : 111
         * channel : QQ
         * name : 11
         * avatar : 111
         * userId : 306117486137311232
         * token : 641d8218a5aa819b42d64eda25e68b67
         */

        private String birthday;
        private String auth_id;
        private String sex;
        private String channel;
        private String name;
        private String avatar;
        private String userId;
        private String token;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getAuth_id() {
            return auth_id;
        }

        public void setAuth_id(String auth_id) {
            this.auth_id = auth_id;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
