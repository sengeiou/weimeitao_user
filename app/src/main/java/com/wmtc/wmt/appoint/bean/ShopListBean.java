package com.wmtc.wmt.appoint.bean;

import java.util.List;

public class ShopListBean {

    /**
     * result : ok
     * data : {"shops":[{"id":308624582166708224,"shopName":"刘杰的小铺","shopUserId":"308571455249973248","shopPhone":"17667936541","level":111,"banner":[{"url":"uploadAttach/308624582166708224/1.png"},{"url":"uploadAttach/308624582166708224/2.png"},{"url":"uploadAttach/308624582166708224/3.png"}],"mainPros":null,"lastPros":null,"secondPros":null,"locationLong":"116","locationLat":"36","levelName":"星选店","average":"100","status":1,"openTime":"00:00","endTime":"20:00","bed":"5","vipBed":"1","province":"山东省","city":"青岛市","area":"市北区","areaCode":"370203","priceMin":"100","priceMax":"110","address":"大融城","location":"120.379521,36.090574","createBy":1,"createTime":"2019-03-02T07:23:54.000+0000","updateBy":null,"updateTime":null,"remarks":null}],"type":"1"}
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
         * shops : [{"id":308624582166708224,"shopName":"刘杰的小铺","shopUserId":"308571455249973248","shopPhone":"17667936541","level":111,"banner":[{"url":"uploadAttach/308624582166708224/1.png"},{"url":"uploadAttach/308624582166708224/2.png"},{"url":"uploadAttach/308624582166708224/3.png"}],"mainPros":null,"lastPros":null,"secondPros":null,"locationLong":"116","locationLat":"36","levelName":"星选店","average":"100","status":1,"openTime":"00:00","endTime":"20:00","bed":"5","vipBed":"1","province":"山东省","city":"青岛市","area":"市北区","areaCode":"370203","priceMin":"100","priceMax":"110","address":"大融城","location":"120.379521,36.090574","createBy":1,"createTime":"2019-03-02T07:23:54.000+0000","updateBy":null,"updateTime":null,"remarks":null}]
         * type : 1
         */

        private String type;
        private List<ShopsBean> shops;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ShopsBean> getShops() {
            return shops;
        }

        public void setShops(List<ShopsBean> shops) {
            this.shops = shops;
        }

        public static class ShopsBean {
            /**
             * id : 308624582166708224
             * shopName : 刘杰的小铺
             * shopUserId : 308571455249973248
             * shopPhone : 17667936541
             * level : 111
             * banner : [{"url":"uploadAttach/308624582166708224/1.png"},{"url":"uploadAttach/308624582166708224/2.png"},{"url":"uploadAttach/308624582166708224/3.png"}]
             * mainPros : null
             * lastPros : null
             * secondPros : null
             * locationLong : 116
             * locationLat : 36
             * levelName : 星选店
             * average : 100
             * status : 1
             * openTime : 00:00
             * endTime : 20:00
             * bed : 5
             * vipBed : 1
             * province : 山东省
             * city : 青岛市
             * area : 市北区
             * areaCode : 370203
             * priceMin : 100
             * priceMax : 110
             * address : 大融城
             * location : 120.379521,36.090574
             * createBy : 1
             * createTime : 2019-03-02T07:23:54.000+0000
             * updateBy : null
             * updateTime : null
             * remarks : null
             */

            private String id;
            private String shopName;
            private String shopUserId;
            private String shopPhone;
            private String level;
            private String mainPros;
            private String lastPros;
            private String secondPros;
            private String locationLong;
            private String locationLat;
            private String levelName;
            private String average;
            private String status;
            private String openTime;
            private String endTime;
            private String bed;
            private String vipBed;
            private String province;
            private String city;
            private String area;
            private String areaCode;
            private String priceMin;
            private String priceMax;
            private String address;
            private String location;
            private String createBy;
            private String createTime;
            private String updateBy;
            private String updateTime;
            private String remarks;
            private List<BannerBean> banner;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getShopUserId() {
                return shopUserId;
            }

            public void setShopUserId(String shopUserId) {
                this.shopUserId = shopUserId;
            }

            public String getShopPhone() {
                return shopPhone;
            }

            public void setShopPhone(String shopPhone) {
                this.shopPhone = shopPhone;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getMainPros() {
                return mainPros;
            }

            public void setMainPros(String mainPros) {
                this.mainPros = mainPros;
            }

            public String getLastPros() {
                return lastPros;
            }

            public void setLastPros(String lastPros) {
                this.lastPros = lastPros;
            }

            public String getSecondPros() {
                return secondPros;
            }

            public void setSecondPros(String secondPros) {
                this.secondPros = secondPros;
            }

            public String getLocationLong() {
                return locationLong;
            }

            public void setLocationLong(String locationLong) {
                this.locationLong = locationLong;
            }

            public String getLocationLat() {
                return locationLat;
            }

            public void setLocationLat(String locationLat) {
                this.locationLat = locationLat;
            }

            public String getLevelName() {
                return levelName;
            }

            public void setLevelName(String levelName) {
                this.levelName = levelName;
            }

            public String getAverage() {
                return average;
            }

            public void setAverage(String average) {
                this.average = average;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getOpenTime() {
                return openTime;
            }

            public void setOpenTime(String openTime) {
                this.openTime = openTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getBed() {
                return bed;
            }

            public void setBed(String bed) {
                this.bed = bed;
            }

            public String getVipBed() {
                return vipBed;
            }

            public void setVipBed(String vipBed) {
                this.vipBed = vipBed;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getPriceMin() {
                return priceMin;
            }

            public void setPriceMin(String priceMin) {
                this.priceMin = priceMin;
            }

            public String getPriceMax() {
                return priceMax;
            }

            public void setPriceMax(String priceMax) {
                this.priceMax = priceMax;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
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

            public List<BannerBean> getBanner() {
                return banner;
            }

            public void setBanner(List<BannerBean> banner) {
                this.banner = banner;
            }

            public static class BannerBean {
                /**
                 * url : uploadAttach/308624582166708224/1.png
                 */

                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
