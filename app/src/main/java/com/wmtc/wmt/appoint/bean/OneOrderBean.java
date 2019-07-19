package com.wmtc.wmt.appoint.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.wmtc.wmt.base.BaseBean;

public class OneOrderBean extends BaseBean {

    /**
     * data : {"id":"323030053149474816","orderNo":"wmt323030053149474816","shopName":"刘杰game好的","projectName":"认个错","projectId":"321944606507794432","shopId":"308624582166708224","arrivalTime":"2019-03-19 00:20:00","afterArrivalTime":"2019-03-19 00:50:00","beforeArrivalTime":"2019-03-18 23:50:00","createTime":"2019-04-11 09:26:06","projectPriceFirst":"1","projectPriceEnd":"1","orderStatus":"1","commentStatus":"未评价","technicianName":"刘姐姐"}
     */

    public DataBean data;

    public static class DataBean implements Parcelable {
        /**
         * id : 323030053149474816
         * orderNo : wmt323030053149474816
         * shopName : 刘杰game好的
         * projectName : 认个错
         * projectId : 321944606507794432
         * shopId : 308624582166708224
         * arrivalTime : 2019-03-19 00:20:00
         * afterArrivalTime : 2019-03-19 00:50:00
         * beforeArrivalTime : 2019-03-18 23:50:00
         * createTime : 2019-04-11 09:26:06
         * projectPriceFirst : 1
         * projectPriceEnd : 1
         * orderStatus : 1
         * commentStatus : 未评价
         * technicianName : 刘姐姐
         */

        public String id;
        public String orderNo;
        public String shopName;
        public String projectName;
        public String projectId;
        public String shopId;
        public String arrivalTime;
        public String afterArrivalTime;
        public String beforeArrivalTime;
        public String createTime;
        public String projectPriceFirst;
        public String projectPriceEnd;
        public String orderStatus;
        public String orderStatusName;
        public String commentStatus;
        public String technicianName;
        public String leavingMsg;
        public String proPic;
        public String couponId;
        public String couponPrice;
        public String couponName;
        public String isPay;
        public String paidProjectPriceFirst;

        protected DataBean(Parcel in) {
            id = in.readString();
            orderNo = in.readString();
            shopName = in.readString();
            projectName = in.readString();
            projectId = in.readString();
            shopId = in.readString();
            arrivalTime = in.readString();
            afterArrivalTime = in.readString();
            beforeArrivalTime = in.readString();
            createTime = in.readString();
            projectPriceFirst = in.readString();
            projectPriceEnd = in.readString();
            orderStatus = in.readString();
            orderStatusName = in.readString();
            commentStatus = in.readString();
            technicianName = in.readString();
            leavingMsg = in.readString();
            proPic = in.readString();
            couponId = in.readString();
            couponPrice = in.readString();
            couponName = in.readString();
            isPay = in.readString();
            paidProjectPriceFirst = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(orderNo);
            dest.writeString(shopName);
            dest.writeString(projectName);
            dest.writeString(projectId);
            dest.writeString(shopId);
            dest.writeString(arrivalTime);
            dest.writeString(afterArrivalTime);
            dest.writeString(beforeArrivalTime);
            dest.writeString(createTime);
            dest.writeString(projectPriceFirst);
            dest.writeString(projectPriceEnd);
            dest.writeString(orderStatus);
            dest.writeString(orderStatusName);
            dest.writeString(commentStatus);
            dest.writeString(technicianName);
            dest.writeString(leavingMsg);
            dest.writeString(proPic);
            dest.writeString(couponId);
            dest.writeString(couponPrice);
            dest.writeString(couponName);
            dest.writeString(isPay);
            dest.writeString(paidProjectPriceFirst);
        }
    }
}
