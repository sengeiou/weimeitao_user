package com.wmtc.wmt.appoint.bean;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

public class ProInfoBean extends BaseBean {


    /**
     * result : ok
     * data : {"id":308641983251873792,"pTitle":"1黄金焕肤源自韩国皮肤管理界的皮肤深层清","shopId":308624582166708224,"pPrice":100,"pOldPrice":1500,"pPriceFirst":20,"pPriceEnd":80,"pPayPercent":null,"pNums":null,"pSold":null,"pPlace":"172","banner":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/3.png","http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.combanner/1/2.png"],"skinS":[{"dictProjectId":118,"dictProjectName":"黄金换肤","dictHufuxuqiuId":164,"dictHufuxuqiuName":"深层清洁"},{"dictProjectId":118,"dictProjectName":"黄金换肤","dictHufuxuqiuId":163,"dictHufuxuqiuName":"补水保湿"}],"infos":["http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.comuploadAttach/1/wm.png"],"pPlaceValue":"头部","pEffect":"排除铅汞等重金属和化妆品残留、提亮肤色","pProduct":"适合所有肤质，皮肤暗沉、老化的皮肤（有黄金过敏性反应的皮肤除外）","pInstrument":"建议配合超声波导入仪、综合仪使用","pProcess":"12312","pIncrement":"1231","pNeed":null,"pContent":null,"pId":118,"pIdValue":"黄金焕肤","pValueHufuxuqiu":"深层清洁,补水保湿,","pPrpoTime":"30","pPrpoPrice":null,"remarks":null,"createBy":308641983251873792,"createTime":"2019-03-02T08:33:03.000+0000","updateBy":null,"updateTime":null,"status":1}
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
         * skinS : [{"dictProjectId":118,"dictProjectName":"黄金换肤","dictHufuxuqiuId":164,"dictHufuxuqiuName":"深层清洁"},{"dictProjectId":118,"dictProjectName":"黄金换肤","dictHufuxuqiuId":163,"dictHufuxuqiuName":"补水保湿"}]
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
        public String shopName;
        public String shopLocal;
        public String pPrice;
        public String pOldPrice;
        public String pPriceFirst;
        public String pPriceEnd;
        public String pPayPercent;
        public String pNums;
        public String pSold;
        public String pPlace;
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
        public List<SkinSBean> skinS;
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

        public List<SkinSBean> getSkinS() {
            return skinS;
        }

        public void setSkinS(List<SkinSBean> skinS) {
            this.skinS = skinS;
        }

        public List<String> getInfos() {
            return infos;
        }

        public void setInfos(List<String> infos) {
            this.infos = infos;
        }

        public static class SkinSBean {
            /**
             * dictProjectId : 118
             * dictProjectName : 黄金换肤
             * dictHufuxuqiuId : 164
             * dictHufuxuqiuName : 深层清洁
             */

            public String dictProjectId;
            public String dictProjectName;
            public String dictHufuxuqiuId;
            public String dictHufuxuqiuName;

            public String getDictProjectId() {
                return dictProjectId;
            }

            public void setDictProjectId(String dictProjectId) {
                this.dictProjectId = dictProjectId;
            }

            public String getDictProjectName() {
                return dictProjectName;
            }

            public void setDictProjectName(String dictProjectName) {
                this.dictProjectName = dictProjectName;
            }

            public String getDictHufuxuqiuId() {
                return dictHufuxuqiuId;
            }

            public void setDictHufuxuqiuId(String dictHufuxuqiuId) {
                this.dictHufuxuqiuId = dictHufuxuqiuId;
            }

            public String getDictHufuxuqiuName() {
                return dictHufuxuqiuName;
            }

            public void setDictHufuxuqiuName(String dictHufuxuqiuName) {
                this.dictHufuxuqiuName = dictHufuxuqiuName;
            }
        }
    }
}
