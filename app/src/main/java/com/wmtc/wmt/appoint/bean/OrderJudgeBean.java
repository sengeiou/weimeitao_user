package com.wmtc.wmt.appoint.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

public class OrderJudgeBean extends BaseBean implements Parcelable {

    /**
     * result : ok
     * data : {"count":1,"resultList":[{"id":311929481629532160,"projectId":308650797095190528,"orderId":310830190240464896,"shopId":789,"userId":304300409554468864,"isAnnoy":"1","judgeContent":"我是评论测试","shopResponse":"我是店家回复","status":1,"createBy":1,"createTime":"2019-03-11","updateBy":1,"updateTime":"2019-03-11","remarks":null,"avatar":"avatar/20190223bd8f5163-12e7-4a9f-94e5-c23555897e5c-15_KB.jpg","name":"我的昵称123"}]}
     * message : 操作成功
     * curson : null
     * erros : null
     */

    public DataBean data;

    public OrderJudgeBean() {
    }

    protected OrderJudgeBean(Parcel in) {
        result = in.readString();
        message = in.readString();
    }

    public static final Creator<OrderJudgeBean> CREATOR = new Creator<OrderJudgeBean>() {
        @Override
        public OrderJudgeBean createFromParcel(Parcel in) {
            return new OrderJudgeBean(in);
        }

        @Override
        public OrderJudgeBean[] newArray(int size) {
            return new OrderJudgeBean[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(result);
        dest.writeString(message);
    }

    public static class DataBean {
        /**
         * count : 1
         * resultList : [{"id":311929481629532160,"projectId":308650797095190528,"orderId":310830190240464896,"shopId":789,"userId":304300409554468864,"isAnnoy":"1","judgeContent":"我是评论测试","shopResponse":"我是店家回复","status":1,"createBy":1,"createTime":"2019-03-11","updateBy":1,"updateTime":"2019-03-11","remarks":null,"avatar":"avatar/20190223bd8f5163-12e7-4a9f-94e5-c23555897e5c-15_KB.jpg","name":"我的昵称123"}]
         */

        public String count;
        public List<ResultListBean> resultList;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ResultListBean {
            /**
             * id : 311929481629532160
             * projectId : 308650797095190528
             * orderId : 310830190240464896
             * shopId : 789
             * userId : 304300409554468864
             * isAnnoy : 1
             * judgeContent : 我是评论测试
             * shopResponse : 我是店家回复
             * status : 1
             * createBy : 1
             * createTime : 2019-03-11
             * updateBy : 1
             * updateTime : 2019-03-11
             * remarks : null
             * avatar : avatar/20190223bd8f5163-12e7-4a9f-94e5-c23555897e5c-15_KB.jpg
             * name : 我的昵称123
             */

            public String id;
            public String projectId;
            public String orderId;
            public String shopId;
            public String userId;
            public String isAnnoy;
            public String judgeContent;
            public String shopResponse;
            public String status;
            public String createBy;
            public String createTime;
            public String updateBy;
            public String updateTime;
            public String remarks;
            public String avatar;
            public String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProjectId() {
                return projectId;
            }

            public void setProjectId(String projectId) {
                this.projectId = projectId;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getIsAnnoy() {
                return isAnnoy;
            }

            public void setIsAnnoy(String isAnnoy) {
                this.isAnnoy = isAnnoy;
            }

            public String getJudgeContent() {
                return judgeContent;
            }

            public void setJudgeContent(String judgeContent) {
                this.judgeContent = judgeContent;
            }

            public String getShopResponse() {
                return shopResponse;
            }

            public void setShopResponse(String shopResponse) {
                this.shopResponse = shopResponse;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
