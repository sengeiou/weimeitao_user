package com.wmtc.wmt.appoint.bean;

import java.util.List;

public class DictBean {

    /**
     * result : ok
     * data : [{"id":175,"num":1,"pid":174,"name":"0-100","list":[]},{"id":176,"num":2,"pid":174,"name":"100-300","list":[]},{"id":177,"num":3,"pid":174,"name":"300-1000","list":[]}]
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
         * id : 175
         * num : 1
         * pid : 174
         * name : 0-100
         * list : []
         */

        private String id;
        private String num;
        private String pid;
        private String name;
        private List<?> list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<?> getList() {
            return list;
        }

        public void setList(List<?> list) {
            this.list = list;
        }
    }
}
