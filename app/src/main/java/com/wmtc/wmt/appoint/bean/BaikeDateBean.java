package com.wmtc.wmt.appoint.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

public class BaikeDateBean extends BaseBean {

    /**
     * result : ok
     * data : {"id":307912636220047360,"titleId":128,"title":"黄金焕肤","content":"我是黄金焕肤\n\n\n","suitCrowds":"1.小孩子\n2.老年人\n\n\n","methods":"我也不清除\n\n\n","price":"100-200元","needTime":"1-2h","advantage":"1.挺好\n2.就是好\n\n\n","shorts":"1.没缺点\n2.没毛病\n\n\n","sideEffect":"我是副作用\n\n\n","fangShi":"治疗方式我也不知道是啥\n\n\n","attention":"我是注意水系那个\n\n\n","functionName":"功效","useTool":"使用工具","useLocation":"操作部位","status":1,"createBy":1,"createTime":"2019-02-28T16:14:53","updateBy":1,"updateTime":"2019-03-04T17:45:07","pics":[{"url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/307912636220047360/2.jpg"},{"url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/307912636220047360/1.jpg"}]}
     * message : 操作成功
     * curson : null
     * erros : null
     */

    private DataBean data;

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
         * id : 307912636220047360
         * titleId : 128
         * title : 黄金焕肤
         * content : 我是黄金焕肤
         * suitCrowds : 1.小孩子
         2.老年人
         * methods : 我也不清除
         * price : 100-200元
         * needTime : 1-2h
         * advantage : 1.挺好
         2.就是好
         * shorts : 1.没缺点
         2.没毛病
         * sideEffect : 我是副作用
         * fangShi : 治疗方式我也不知道是啥
         * attention : 我是注意水系那个
         * functionName : 功效
         * useTool : 使用工具
         * useLocation : 操作部位
         * status : 1
         * createBy : 1
         * createTime : 2019-02-28T16:14:53
         * updateBy : 1
         * updateTime : 2019-03-04T17:45:07
         * pics : [{"url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/307912636220047360/2.jpg"},{"url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/307912636220047360/1.jpg"}]
         */

       public String id;
       public String titleId;
       public String title;
       public String content;
       public String suitCrowds;
       public String methods;
       public String price;
       public String needTime;
       public String advantage;
       public String shorts;
       public String sideEffect;
       public String fangShi;
       public String attention;
       public String functionName;
       public String useTool;
       public String useLocation;
       public String status;
       public String createBy;
       public String createTime;
       public String updateBy;
       public String updateTime;
       public List<PicsBean> pics;

        public String getId() {
            if (id != null){
                return id;
            }
            return id = "";
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitleId() {
            if (titleId != null){
                return titleId;
            }
            return titleId = "";
        }

        public void setTitleId(String titleId) {
            this.titleId = titleId;
        }

        public String getTitle() {
            if (title != null){
                return title;
            }
            return title = "";
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            if (content != null){
                return content;
            }
            return content = "";
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSuitCrowds() {
            if (suitCrowds != null){
                return suitCrowds;
            }
            return suitCrowds = "";
        }

        public void setSuitCrowds(String suitCrowds) {
            this.suitCrowds = suitCrowds;
        }

        public String getMethods() {
            if (methods != null){
                return methods;
            }
            return methods = "";
        }

        public void setMethods(String methods) {
            this.methods = methods;
        }

        public String getPrice() {
            if (price != null){
                return price;
            }
            return price = "";
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNeedTime() {
            if (needTime != null){
                return needTime;
            }
            return needTime = "";
        }

        public void setNeedTime(String needTime) {
            this.needTime = needTime;
        }

        public String getAdvantage() {
            if (advantage != null){
                return advantage;
            }
            return advantage = "";
        }

        public void setAdvantage(String advantage) {
            this.advantage = advantage;
        }

        public String getShorts() {
            if (shorts != null){
                return shorts;
            }
            return shorts = "";
        }

        public void setShorts(String shorts) {
            this.shorts = shorts;
        }

        public String getSideEffect() {
            if (sideEffect != null){
                return sideEffect;
            }
            return sideEffect = "";
        }

        public void setSideEffect(String sideEffect) {
            this.sideEffect = sideEffect;
        }

        public String getFangShi() {
            if (fangShi != null){
                return fangShi;
            }
            return fangShi = "";
        }

        public void setFangShi(String fangShi) {
            this.fangShi = fangShi;
        }

        public String getAttention() {
            if (attention != null){
                return attention;
            }
            return attention = "";
        }

        public void setAttention(String attention) {
            this.attention = attention;
        }

        public String getFunctionName() {
            if (functionName != null){
                return functionName;
            }
            return functionName = "";
        }

        public void setFunctionName(String functionName) {
            this.functionName = functionName;
        }

        public String getUseTool() {
            if (useTool != null){
                return useTool;
            }
            return useTool = "";
        }

        public void setUseTool(String useTool) {
            this.useTool = useTool;
        }

        public String getUseLocation() {
            if (useLocation != null){
                return useLocation;
            }
            return useLocation = "";
        }

        public void setUseLocation(String useLocation) {
            this.useLocation = useLocation;
        }

        public String getStatus() {
            if (status != null){
                return status;
            }
            return status = "";
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateBy() {
            if (createBy != null){
                return createBy;
            }
            return createBy = "";
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            if (createTime != null){
                return createTime;
            }
            return createTime = "";
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateBy() {
            if (updateBy != null){
                return updateBy;
            }
            return updateBy = "";
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            if (updateTime != null){
                return updateTime;
            }
            return updateTime = "";
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<PicsBean> getPics() {
            return pics;
        }

        public void setPics(List<PicsBean> pics) {
            this.pics = pics;
        }

        public static class PicsBean implements Parcelable {
            /**
             * url : http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/307912636220047360/2.jpg
             */

            private String url;

            protected PicsBean(Parcel in) {
                url = in.readString();
            }

            public static final Creator<PicsBean> CREATOR = new Creator<PicsBean>() {
                @Override
                public PicsBean createFromParcel(Parcel in) {
                    return new PicsBean(in);
                }

                @Override
                public PicsBean[] newArray(int size) {
                    return new PicsBean[size];
                }
            };

            public String getUrl() {
                if (url != null){
                    return url;
                }
                return url = "";
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(url);
            }
        }
    }
}
