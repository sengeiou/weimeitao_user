package com.wmtc.wmt.appoint.bean;

import java.util.List;

public class TeacherBean {

    /**
     * result : ok
     * data : [{"id":310461330307940352,"shopId":308624582166708224,"shopName":"刘杰的小铺","technicianName":"刘姐姐","notDo":"","toDay":"7天","avatar":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/avatar/310463593147531264/ee.jpg","title":"Guns技术文档最新版","goodAt":"打人啊啊啊啊","workTime":"2019-03-07","restDay":"星期二,星期三,星期六","createBy":310461330307940352,"createTime":"2019-03-07T09:02:29.000+0000","updateBy":null,"updateTime":null,"remarks":null},{"id":310463593147531264,"shopId":308624582166708224,"shopName":"刘杰的小铺","technicianName":"122","notDo":"","toDay":"2年","avatar":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/avatar/310463593147531264/ee.jpg","title":"123","goodAt":"打人","workTime":"2017-03-07","restDay":"星期六","createBy":310463593147531264,"createTime":"2019-03-07T09:11:29.000+0000","updateBy":null,"updateTime":null,"remarks":null}]
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
         * id : 310461330307940352
         * shopId : 308624582166708224
         * shopName : 刘杰的小铺
         * technicianName : 刘姐姐
         * notDo : 
         * toDay : 7天
         * avatar : http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/avatar/310463593147531264/ee.jpg
         * title : Guns技术文档最新版
         * goodAt : 打人啊啊啊啊
         * workTime : 2019-03-07
         * restDay : 星期二,星期三,星期六
         * createBy : 310461330307940352
         * createTime : 2019-03-07T09:02:29.000+0000
         * updateBy : null
         * updateTime : null
         * remarks : null
         */

        private String id;
        private String shopId;
        private String shopName;
        private String technicianName;
        private String notDo;
        private String toDay;
        private String avatar;
        private String title;
        private String goodAt;
        private String workTime;
        private String restDay;
        private String createBy;
        private String createTime;
        private String updateBy;
        private String updateTime;
        private String remarks;
        private String ischeck;

        public String getIscheck() {
            return ischeck;
        }

        public void setIscheck(String ischeck) {
            this.ischeck = ischeck;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getTechnicianName() {
            return technicianName;
        }

        public void setTechnicianName(String technicianName) {
            this.technicianName = technicianName;
        }

        public String getNotDo() {
            return notDo;
        }

        public void setNotDo(String notDo) {
            this.notDo = notDo;
        }

        public String getToDay() {
            return toDay;
        }

        public void setToDay(String toDay) {
            this.toDay = toDay;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGoodAt() {
            return goodAt;
        }

        public void setGoodAt(String goodAt) {
            this.goodAt = goodAt;
        }

        public String getWorkTime() {
            return workTime;
        }

        public void setWorkTime(String workTime) {
            this.workTime = workTime;
        }

        public String getRestDay() {
            return restDay;
        }

        public void setRestDay(String restDay) {
            this.restDay = restDay;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }
}
