package com.wmtc.wmt.personal.bean;

/**
 * Created by Administrator on 2019/2/25.
 */
public class LoginSucceedBean {

    /**
     * result : ok
     * data : {"userId":"305382074368393216","token":"7bda3dc9b772a569b9ed14b3c372db64"}
     * message : 操作成功
     * curson : null
     * erros : null
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
         * birthday : 1983-01-01T00:00
         * sex : 1
         * name :
         * avatar : avatar/20190417c183e001-8092-4672-b25d-3b7960ea1d19-1555490736759.png
         * userId : 305382074368393216
         * contactTel : 17609027963
         * account : 17609027963
         * token : 70d1c0fb4f89e3bc95ea57ef2e55fbc1
         */

        public String birthday;
        public String sex;
        public String name;
        public String avatar;
        public String userId;
        public String contactTel;
        public String account;
        public String token;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
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

        public String getContactTel() {
            return contactTel;
        }

        public void setContactTel(String contactTel) {
            this.contactTel = contactTel;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
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
