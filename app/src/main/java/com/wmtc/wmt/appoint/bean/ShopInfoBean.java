package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

public class ShopInfoBean extends BaseBean {


    /**
     * result : ok
     * data : {"shops":{"id":308624582166708224,"shopName":"刘杰的小铺","shopUserId":"308571455249973248","shopPhone":"17667936541","level":111,"banner":[{"url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/308624582166708224/1.png"}],"mainPros":[{"id":308641983251873792,"pTitle":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","shopId":308624582166708224,"pPrice":100,"pOldPrice":1500,"pPriceFirst":20,"pPriceEnd":80,"pPayPercent":null,"pNums":null,"pSold":null,"pPlace":"172","banner":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/3.png","http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/2.png"],"skinS":null,"infos":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.comuploadAttach/1/wm.png"],"pPlaceValue":"头部","pEffect":"排除铅汞等重金属和化妆品残留、提亮肤色","pProduct":"适合所有肤质，皮肤暗沉、老化的皮肤（有黄金过敏性反应的皮肤除外）","pInstrument":"建议配合超声波导入仪、综合仪使用","pProcess":"12312","pIncrement":"1231","pNeed":null,"pContent":null,"pId":118,"pIdValue":"黄金焕肤","pValueHufuxuqiu":"深层清洁,补水保湿,","pPrpoTime":"30","pPrpoPrice":null,"remarks":null,"createBy":308641983251873792,"createTime":"2019-03-02T08:33:03.000+0000","updateBy":null,"updateTime":null,"status":1}],"lastPros":[],"secondPros":[{"id":308650797095190528,"pTitle":"源自韩国皮肤管理界的皮肤深层","shopId":308624582166708224,"pPrice":100,"pOldPrice":1500,"pPriceFirst":20,"pPriceEnd":80,"pPayPercent":null,"pNums":null,"pSold":null,"pPlace":"172","banner":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/3.png","http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/3.png"],"skinS":null,"infos":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.comuploadAttach/1/wm - 副本 - 副本.png"],"pPlaceValue":"头部","pEffect":"排除铅汞等重金属和化妆品残留、提亮肤色","pProduct":"使用残品","pInstrument":"仪器","pProcess":"2","pIncrement":"1","pNeed":null,"pContent":null,"pId":119,"pIdValue":"黄金焕肤","pValueHufuxuqiu":"深层清洁,补水保湿,","pPrpoTime":"30","pPrpoPrice":null,"remarks":null,"createBy":308650797095190528,"createTime":"2019-03-02T09:08:04.000+0000","updateBy":null,"updateTime":null,"status":1}],"locationString":"116","locationLat":"36","levelName":"星选店","average":"100","status":1,"openTime":"00:00","endTime":"20:00","bed":"5","vipBed":"1","province":"山东省","city":"青岛市","area":"市北区","areaCode":"370203","priceMin":"100","priceMax":"110","address":"大融城","location":"120.379521,36.090574","createBy":1,"createTime":"2019-03-02T07:23:54.000+0000","updateBy":null,"updateTime":null,"remarks":null}}
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
         * shops : {"id":308624582166708224,"shopName":"刘杰的小铺","shopUserId":"308571455249973248","shopPhone":"17667936541","level":111,"banner":[{"url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/308624582166708224/1.png"}],"mainPros":[{"id":308641983251873792,"pTitle":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","shopId":308624582166708224,"pPrice":100,"pOldPrice":1500,"pPriceFirst":20,"pPriceEnd":80,"pPayPercent":null,"pNums":null,"pSold":null,"pPlace":"172","banner":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/3.png","http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/2.png"],"skinS":null,"infos":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.comuploadAttach/1/wm.png"],"pPlaceValue":"头部","pEffect":"排除铅汞等重金属和化妆品残留、提亮肤色","pProduct":"适合所有肤质，皮肤暗沉、老化的皮肤（有黄金过敏性反应的皮肤除外）","pInstrument":"建议配合超声波导入仪、综合仪使用","pProcess":"12312","pIncrement":"1231","pNeed":null,"pContent":null,"pId":118,"pIdValue":"黄金焕肤","pValueHufuxuqiu":"深层清洁,补水保湿,","pPrpoTime":"30","pPrpoPrice":null,"remarks":null,"createBy":308641983251873792,"createTime":"2019-03-02T08:33:03.000+0000","updateBy":null,"updateTime":null,"status":1}],"lastPros":[],"secondPros":[{"id":308650797095190528,"pTitle":"源自韩国皮肤管理界的皮肤深层","shopId":308624582166708224,"pPrice":100,"pOldPrice":1500,"pPriceFirst":20,"pPriceEnd":80,"pPayPercent":null,"pNums":null,"pSold":null,"pPlace":"172","banner":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/3.png","http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/3.png"],"skinS":null,"infos":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.comuploadAttach/1/wm - 副本 - 副本.png"],"pPlaceValue":"头部","pEffect":"排除铅汞等重金属和化妆品残留、提亮肤色","pProduct":"使用残品","pInstrument":"仪器","pProcess":"2","pIncrement":"1","pNeed":null,"pContent":null,"pId":119,"pIdValue":"黄金焕肤","pValueHufuxuqiu":"深层清洁,补水保湿,","pPrpoTime":"30","pPrpoPrice":null,"remarks":null,"createBy":308650797095190528,"createTime":"2019-03-02T09:08:04.000+0000","updateBy":null,"updateTime":null,"status":1}],"locationString":"116","locationLat":"36","levelName":"星选店","average":"100","status":1,"openTime":"00:00","endTime":"20:00","bed":"5","vipBed":"1","province":"山东省","city":"青岛市","area":"市北区","areaCode":"370203","priceMin":"100","priceMax":"110","address":"大融城","location":"120.379521,36.090574","createBy":1,"createTime":"2019-03-02T07:23:54.000+0000","updateBy":null,"updateTime":null,"remarks":null}
         */

        public ShopsBean shops;

        public ShopsBean getShops() {
            return shops;
        }

        public void setShops(ShopsBean shops) {
            this.shops = shops;
        }

        public static class ShopsBean {
            /**
             * id : 308624582166708224
             * shopName : 刘杰的小铺
             * shopUserId : 308571455249973248
             * shopPhone : 17667936541
             * level : 111
             * banner : [{"url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/308624582166708224/1.png"}]
             * mainPros : [{"id":308641983251873792,"pTitle":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","shopId":308624582166708224,"pPrice":100,"pOldPrice":1500,"pPriceFirst":20,"pPriceEnd":80,"pPayPercent":null,"pNums":null,"pSold":null,"pPlace":"172","banner":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/3.png","http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/2.png"],"skinS":null,"infos":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.comuploadAttach/1/wm.png"],"pPlaceValue":"头部","pEffect":"排除铅汞等重金属和化妆品残留、提亮肤色","pProduct":"适合所有肤质，皮肤暗沉、老化的皮肤（有黄金过敏性反应的皮肤除外）","pInstrument":"建议配合超声波导入仪、综合仪使用","pProcess":"12312","pIncrement":"1231","pNeed":null,"pContent":null,"pId":118,"pIdValue":"黄金焕肤","pValueHufuxuqiu":"深层清洁,补水保湿,","pPrpoTime":"30","pPrpoPrice":null,"remarks":null,"createBy":308641983251873792,"createTime":"2019-03-02T08:33:03.000+0000","updateBy":null,"updateTime":null,"status":1}]
             * lastPros : []
             * secondPros : [{"id":308650797095190528,"pTitle":"源自韩国皮肤管理界的皮肤深层","shopId":308624582166708224,"pPrice":100,"pOldPrice":1500,"pPriceFirst":20,"pPriceEnd":80,"pPayPercent":null,"pNums":null,"pSold":null,"pPlace":"172","banner":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/3.png","http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/3.png"],"skinS":null,"infos":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.comuploadAttach/1/wm - 副本 - 副本.png"],"pPlaceValue":"头部","pEffect":"排除铅汞等重金属和化妆品残留、提亮肤色","pProduct":"使用残品","pInstrument":"仪器","pProcess":"2","pIncrement":"1","pNeed":null,"pContent":null,"pId":119,"pIdValue":"黄金焕肤","pValueHufuxuqiu":"深层清洁,补水保湿,","pPrpoTime":"30","pPrpoPrice":null,"remarks":null,"createBy":308650797095190528,"createTime":"2019-03-02T09:08:04.000+0000","updateBy":null,"updateTime":null,"status":1}]
             * locationString : 116
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

            public String id;
            public String shopName;
            public String shopUserId;
            public String shopPhone;
            public String level;
            public String locationString;
            public String locationLat;
            public String locationLong;
            public String levelName;
            public String average;
            public String status;
            public String openTime;
            public String endTime;
            public String bed;
            public String vipBed;
            public String province;
            public String city;
            public String vrUrl;
            public String videoUrl;
            public String area;
            public String areaCode;
            public String priceMin;
            public String priceMax;
            public String address;
            public String location;
            public String createBy;
            public String createTime;
            public String updateBy;
            public String updateTime;
            public String remarks;
            public String attentionStatus;
            public List<BannerBean> banner;
            public List<ProsBean> mainPros;
            public List<ProsBean> lastPros;
            public List<ProsBean> secondPros;

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

            public String getLocationString() {
                return locationString;
            }

            public void setLocationString(String locationString) {
                this.locationString = locationString;
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

            public List<ProsBean> getMainPros() {
                return mainPros;
            }

            public void setMainPros(List<ProsBean> mainPros) {
                this.mainPros = mainPros;
            }

            public List<ProsBean> getLastPros() {
                return lastPros;
            }

            public void setLastPros(List<ProsBean> lastPros) {
                this.lastPros = lastPros;
            }

            public List<ProsBean> getSecondPros() {
                return secondPros;
            }

            public void setSecondPros(List<ProsBean> secondPros) {
                this.secondPros = secondPros;
            }

            public static class BannerBean {
                /**
                 * url : http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/308624582166708224/1.png
                 */

                public String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class ProsBean {
                /**
                 * id : 308641983251873792
                 * pTitle : 1黄金焕肤源自韩国皮肤管理界的皮肤深层清
                 * shopId : 308624582166708224
                 * pPrice : 100
                 * pOldPrice : 1500
                 * pPriceFirst : 20
                 * pPriceEnd : 80
                 * pPayPercent : null
                 * pNums : null
                 * pSold : null
                 * pPlace : 172
                 * banner : ["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/3.png","http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/2.png"]
                 * skinS : null
                 * infos : ["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.comuploadAttach/1/wm.png"]
                 * pPlaceValue : 头部
                 * pEffect : 排除铅汞等重金属和化妆品残留、提亮肤色
                 * pProduct : 适合所有肤质，皮肤暗沉、老化的皮肤（有黄金过敏性反应的皮肤除外）
                 * pInstrument : 建议配合超声波导入仪、综合仪使用
                 * pProcess : 12312
                 * pIncrement : 1231
                 * pNeed : null
                 * pContent : null
                 * pId : 118
                 * pIdValue : 黄金焕肤
                 * pValueHufuxuqiu : 深层清洁,补水保湿,
                 * pPrpoTime : 30
                 * pPrpoPrice : null
                 * remarks : null
                 * createBy : 308641983251873792
                 * createTime : 2019-03-02T08:33:03.000+0000
                 * updateBy : null
                 * updateTime : null
                 * status : 1
                 */

                public String id;
                public String pTitle;
                public String shopId;
                public String pPrice;
                public String pOldPrice;
                public String pPriceFirst;
                public String pPriceEnd;
                public String pPayPercent;
                public String pNums;
                public String pSold;
                public String pPlace;
                public String skinS;
                public String pPlaceValue;
                public String pEffect;
                public String pProduct;
                public String pInstrument;
                public String pProcess;
                public String pIncrement;
                public String pNeed;
                public String pContent;
                public String pId;
                public String pIdValue;
                public String pValueHufuxuqiu;
                public String pPrpoTime;
                public String pPrpoPrice;
                public String remarks;
                public String createBy;
                public String createTime;
                public String updateBy;
                public String updateTime;
                public String status;
                public List<String> banner;
                public List<String> infos;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getPTitle() {
                    return pTitle;
                }

                public void setPTitle(String pTitle) {
                    this.pTitle = pTitle;
                }

                public String getShopId() {
                    return shopId;
                }

                public void setShopId(String shopId) {
                    this.shopId = shopId;
                }

                public String getPPrice() {
                    return pPrice;
                }

                public void setPPrice(String pPrice) {
                    this.pPrice = pPrice;
                }

                public String getPOldPrice() {
                    return pOldPrice;
                }

                public void setPOldPrice(String pOldPrice) {
                    this.pOldPrice = pOldPrice;
                }

                public String getPPriceFirst() {
                    return pPriceFirst;
                }

                public void setPPriceFirst(String pPriceFirst) {
                    this.pPriceFirst = pPriceFirst;
                }

                public String getPPriceEnd() {
                    return pPriceEnd;
                }

                public void setPPriceEnd(String pPriceEnd) {
                    this.pPriceEnd = pPriceEnd;
                }

                public String getPPayPercent() {
                    return pPayPercent;
                }

                public void setPPayPercent(String pPayPercent) {
                    this.pPayPercent = pPayPercent;
                }

                public String getPNums() {
                    return pNums;
                }

                public void setPNums(String pNums) {
                    this.pNums = pNums;
                }

                public String getPSold() {
                    return pSold;
                }

                public void setPSold(String pSold) {
                    this.pSold = pSold;
                }

                public String getPPlace() {
                    return pPlace;
                }

                public void setPPlace(String pPlace) {
                    this.pPlace = pPlace;
                }

                public String getSkinS() {
                    return skinS;
                }

                public void setSkinS(String skinS) {
                    this.skinS = skinS;
                }

                public String getPPlaceValue() {
                    return pPlaceValue;
                }

                public void setPPlaceValue(String pPlaceValue) {
                    this.pPlaceValue = pPlaceValue;
                }

                public String getPEffect() {
                    return pEffect;
                }

                public void setPEffect(String pEffect) {
                    this.pEffect = pEffect;
                }

                public String getPProduct() {
                    return pProduct;
                }

                public void setPProduct(String pProduct) {
                    this.pProduct = pProduct;
                }

                public String getPInstrument() {
                    return pInstrument;
                }

                public void setPInstrument(String pInstrument) {
                    this.pInstrument = pInstrument;
                }

                public String getPProcess() {
                    return pProcess;
                }

                public void setPProcess(String pProcess) {
                    this.pProcess = pProcess;
                }

                public String getPIncrement() {
                    return pIncrement;
                }

                public void setPIncrement(String pIncrement) {
                    this.pIncrement = pIncrement;
                }

                public String getPNeed() {
                    return pNeed;
                }

                public void setPNeed(String pNeed) {
                    this.pNeed = pNeed;
                }

                public String getPContent() {
                    return pContent;
                }

                public void setPContent(String pContent) {
                    this.pContent = pContent;
                }

                public String getPId() {
                    return pId;
                }

                public void setPId(String pId) {
                    this.pId = pId;
                }

                public String getPIdValue() {
                    return pIdValue;
                }

                public void setPIdValue(String pIdValue) {
                    this.pIdValue = pIdValue;
                }

                public String getPValueHufuxuqiu() {
                    return pValueHufuxuqiu;
                }

                public void setPValueHufuxuqiu(String pValueHufuxuqiu) {
                    this.pValueHufuxuqiu = pValueHufuxuqiu;
                }

                public String getPPrpoTime() {
                    return pPrpoTime;
                }

                public void setPPrpoTime(String pPrpoTime) {
                    this.pPrpoTime = pPrpoTime;
                }

                public String getPPrpoPrice() {
                    return pPrpoPrice;
                }

                public void setPPrpoPrice(String pPrpoPrice) {
                    this.pPrpoPrice = pPrpoPrice;
                }

                public String getRemarks() {
                    return remarks;
                }

                public void setRemarks(String remarks) {
                    this.remarks = remarks;
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

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public List<String> getBanner() {
                    return banner;
                }

                public void setBanner(List<String> banner) {
                    this.banner = banner;
                }

                public List<String> getInfos() {
                    return infos;
                }

                public void setInfos(List<String> infos) {
                    this.infos = infos;
                }
            }

        }
    }
}
