package com.wmtc.wmt.appoint.bean;

import java.util.List;

public class XMbaikeBean {

    /**
     * result : ok
     * data : [{"id":121,"num":1,"pid":120,"name":"抗衰抗皱","list":[{"id":119,"num":2,"pid":117,"name":"雪花焕肤","list":[]},{"id":127,"num":3,"pid":117,"name":"巧克力焕肤","list":[]}]},{"id":122,"num":2,"pid":120,"name":"补水保湿","list":[{"id":118,"num":1,"pid":117,"name":"黄金焕肤","list":[]},{"id":119,"num":2,"pid":117,"name":"雪花焕肤","list":[]}]},{"id":123,"num":3,"pid":120,"name":"美白淡斑","list":[{"id":118,"num":1,"pid":117,"name":"黄金焕肤","list":[]},{"id":119,"num":2,"pid":117,"name":"雪花焕肤","list":[]},{"id":130,"num":6,"pid":117,"name":"马卡龙焕肤","list":[]}]},{"id":124,"num":4,"pid":120,"name":"紧致滋养","list":[{"id":119,"num":2,"pid":117,"name":"雪花焕肤","list":[]},{"id":129,"num":5,"pid":117,"name":"红酒焕肤","list":[]}]},{"id":125,"num":5,"pid":120,"name":"深层清洁","list":[{"id":128,"num":4,"pid":117,"name":"灵芝焕肤","list":[]}]},{"id":126,"num":6,"pid":120,"name":"抗痘管理","list":[{"id":128,"num":4,"pid":117,"name":"灵芝焕肤","list":[]}]}]
     * message : 操作成功
     * curson : null
     * erros : null
     */

    public String result;
    public String message;
    public String curson;
    public String erros;
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
         * id : 121
         * num : 1
         * pid : 120
         * name : 抗衰抗皱
         * list : [{"id":119,"num":2,"pid":117,"name":"雪花焕肤","list":[]},{"id":127,"num":3,"pid":117,"name":"巧克力焕肤","list":[]}]
         */

       public String id;
       public String num;
       public String pid;
       public String name;
       public List<ListBean> list;

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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 119
             * num : 2
             * pid : 117
             * name : 雪花焕肤
             * list : []
             */

          public String id;
          public String num;
          public String pid;
          public String name;
          public List<?> list;

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
}
