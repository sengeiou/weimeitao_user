package com.wmtc.wmt.personal.bean;

import com.wmtc.wmt.base.BaseBean;

/**
 * Created by Administrator on 2019/2/21.
 */
public class LoginByCodeBean extends BaseBean {


    /**
     * result : ok
     * data : {"birthday":"","sex":"null","name":null,"avatar":null,"userId":"305382074368393216","token":"2310c3ee9f942a5e44a015b769d4fb23"}
     * message : 操作成功
     * curson : null
     * erros : null
     */

    public DataBean data;

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

    public static class DataBean {
        /**
         * birthday :
         * sex : null
         * name : null
         * avatar : null
         * userId : 305382074368393216
         * token : 2310c3ee9f942a5e44a015b769d4fb23
         */

        public String birthday;
        public String sex;
        public String name;
        public String avatar;
        public String userId;
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
