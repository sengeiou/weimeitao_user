package com.wmtc.wmt.appoint.bean;

public class OrderStateBean {

    /**
     * result : ok
     * data : {"id":"314400897310392320","orderNo":"wmt314400897310392320","shopId":"308624582166708224","shopName":"刘杰的小铺","shopAddress":"山东省青岛市大融城","projectId":"308641983251873792","projectName":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","projectPriceEnd":"80","arrivalTime":"2019-03-19 00:20:00.0","technicianId":"310461330307940352","technicianName":"刘姐姐","customerNum":"1","orderStatus":"2","orderStatusName":"已取消（10min超时支付）","paidProjectPriceFirst":null,"projectPriceFirstPaidTime":null,"createTime":"2019-03-18 13:56:55","leavingMsg":null,"picUrl":"banner/1/3.png","sysNotpayTime":null,"userCancelTime":null,"shopCancelTime":null,"shopConfirmTime":null,"sysNottakeTime":null,"projectPriceFirst":null,"paidProjectPriceEnd":null,"projectPriceEndPaidTime":null}
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
         * id : 314400897310392320
         * orderNo : wmt314400897310392320
         * shopId : 308624582166708224
         * shopName : 刘杰的小铺
         * shopAddress : 山东省青岛市大融城
         * projectId : 308641983251873792
         * projectName : 1黄金焕肤源自韩国皮肤管理界的皮肤深层清
         * projectPriceEnd : 80
         * arrivalTime : 2019-03-19 00:20:00.0
         * technicianId : 310461330307940352
         * technicianName : 刘姐姐
         * customerNum : 1
         * orderStatus : 2
         * orderStatusName : 已取消（10min超时支付）
         * paidProjectPriceFirst : null
         * projectPriceFirstPaidTime : null
         * createTime : 2019-03-18 13:56:55
         * leavingMsg : null
         * picUrl : banner/1/3.png
         * sysNotpayTime : null
         * userCancelTime : null
         * shopCancelTime : null
         * shopConfirmTime : null
         * sysNottakeTime : null
         * projectPriceFirst : null
         * paidProjectPriceEnd : null
         * projectPriceEndPaidTime : null
         */

        private String id;
        private String orderNo;
        private String shopId;
        private String shopName;
        private String shopAddress;
        private String projectId;
        private String projectName;
        private String projectPriceEnd;
        private String arrivalTime;
        private String technicianId;
        private String technicianName;
        private String customerNum;
        private String orderStatus;
        private String orderStatusName;
        private String paidProjectPriceFirst;
        private String projectPriceFirstPaidTime;
        private String createTime;
        private String leavingMsg;
        private String picUrl;
        private String sysNotpayTime;
        private String userCancelTime;
        private String shopCancelTime;
        private String shopConfirmTime;
        private String sysNottakeTime;
        private String projectPriceFirst;
        private String paidProjectPriceEnd;
        private String projectPriceEndPaidTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
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

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getProjectPriceEnd() {
            return projectPriceEnd;
        }

        public void setProjectPriceEnd(String projectPriceEnd) {
            this.projectPriceEnd = projectPriceEnd;
        }

        public String getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        public String getTechnicianId() {
            return technicianId;
        }

        public void setTechnicianId(String technicianId) {
            this.technicianId = technicianId;
        }

        public String getTechnicianName() {
            return technicianName;
        }

        public void setTechnicianName(String technicianName) {
            this.technicianName = technicianName;
        }

        public String getCustomerNum() {
            return customerNum;
        }

        public void setCustomerNum(String customerNum) {
            this.customerNum = customerNum;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusName() {
            return orderStatusName;
        }

        public void setOrderStatusName(String orderStatusName) {
            this.orderStatusName = orderStatusName;
        }

        public String getPaidProjectPriceFirst() {
            return paidProjectPriceFirst;
        }

        public void setPaidProjectPriceFirst(String paidProjectPriceFirst) {
            this.paidProjectPriceFirst = paidProjectPriceFirst;
        }

        public String getProjectPriceFirstPaidTime() {
            return projectPriceFirstPaidTime;
        }

        public void setProjectPriceFirstPaidTime(String projectPriceFirstPaidTime) {
            this.projectPriceFirstPaidTime = projectPriceFirstPaidTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLeavingMsg() {
            return leavingMsg;
        }

        public void setLeavingMsg(String leavingMsg) {
            this.leavingMsg = leavingMsg;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getSysNotpayTime() {
            return sysNotpayTime;
        }

        public void setSysNotpayTime(String sysNotpayTime) {
            this.sysNotpayTime = sysNotpayTime;
        }

        public String getUserCancelTime() {
            return userCancelTime;
        }

        public void setUserCancelTime(String userCancelTime) {
            this.userCancelTime = userCancelTime;
        }

        public String getShopCancelTime() {
            return shopCancelTime;
        }

        public void setShopCancelTime(String shopCancelTime) {
            this.shopCancelTime = shopCancelTime;
        }

        public String getShopConfirmTime() {
            return shopConfirmTime;
        }

        public void setShopConfirmTime(String shopConfirmTime) {
            this.shopConfirmTime = shopConfirmTime;
        }

        public String getSysNottakeTime() {
            return sysNottakeTime;
        }

        public void setSysNottakeTime(String sysNottakeTime) {
            this.sysNottakeTime = sysNottakeTime;
        }

        public String getProjectPriceFirst() {
            return projectPriceFirst;
        }

        public void setProjectPriceFirst(String projectPriceFirst) {
            this.projectPriceFirst = projectPriceFirst;
        }

        public String getPaidProjectPriceEnd() {
            return paidProjectPriceEnd;
        }

        public void setPaidProjectPriceEnd(String paidProjectPriceEnd) {
            this.paidProjectPriceEnd = paidProjectPriceEnd;
        }

        public String getProjectPriceEndPaidTime() {
            return projectPriceEndPaidTime;
        }

        public void setProjectPriceEndPaidTime(String projectPriceEndPaidTime) {
            this.projectPriceEndPaidTime = projectPriceEndPaidTime;
        }
    }
}
