package com.wmtc.wmt.appoint.pojo;

public class CouponPojo {
    //ownerId:(用户id)，
    // couponStatus（1 未使用 2 已使用 3 已过期），
    // couponType;// 1店铺专属 2 项目专属 3通用，
    // couponConfigType(1:优惠券，2红包)
    private String ownerId;
    private String couponStatus;
    private String couponType;
    private String couponConfigType;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponConfigType() {
        return couponConfigType;
    }

    public void setCouponConfigType(String couponConfigType) {
        this.couponConfigType = couponConfigType;
    }
}
