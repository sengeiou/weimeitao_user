package com.wmtc.wmt.appoint.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/29.
 * com.wmtc.wmt.appoint.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ProBkInfoBean extends BaseBean {

    /**
     * data : {"id":26,"titleId":349,"title":"黄金焕肤","content":"源自韩国皮肤管理界的皮肤深层清洁焕肤项目，以纳米级的黄金微粒和多种营养成分，彻底清洁引起皮肤暗沉、老化的重金属和其他有害物质，减少因紫外线引起的损伤，减少自由基，防止肌肤老化。","suitCrowds":"不适合对黄金有过敏性反应的皮肤","methods":"1.卸妆洁面\r\n2.深层清洁\r\n3.使用24K纳米黄金护理\r\n4.整理（水乳霜防晒）","price":"200-600元","needTime":"60-90分钟","advantage":"1.成分安全无刺激\r\n2.保湿度好\r\n3.深层排除面部铅汞等毒素4.极其水润","fangShi":"24K纳米黄金微颗粒通过离子交换方式，结合专业的皮肤管理手法和深层导入仪器，清洁引起皮肤老化的有害重金属和其他有害物质。","attention":"对黄金过敏的人不适宜使用","functionName":"排除铅汞等重金属和化妆品残留、美白提亮肤色·改善激素脸","useTool":"建议配合超声波导入仪、综合仪使用","useLocation":"脸部","status":1,"pics":[{"url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/26/logo.png"}],"videos":[]}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * id : 26
         * titleId : 349
         * title : 黄金焕肤
         * content : 源自韩国皮肤管理界的皮肤深层清洁焕肤项目，以纳米级的黄金微粒和多种营养成分，彻底清洁引起皮肤暗沉、老化的重金属和其他有害物质，减少因紫外线引起的损伤，减少自由基，防止肌肤老化。
         * suitCrowds : 不适合对黄金有过敏性反应的皮肤
         * methods : 1.卸妆洁面
         * 2.深层清洁
         * 3.使用24K纳米黄金护理
         * 4.整理（水乳霜防晒）
         * price : 200-600元
         * needTime : 60-90分钟
         * advantage : 1.成分安全无刺激
         * 2.保湿度好
         * 3.深层排除面部铅汞等毒素4.极其水润
         * fangShi : 24K纳米黄金微颗粒通过离子交换方式，结合专业的皮肤管理手法和深层导入仪器，清洁引起皮肤老化的有害重金属和其他有害物质。
         * attention : 对黄金过敏的人不适宜使用
         * functionName : 排除铅汞等重金属和化妆品残留、美白提亮肤色·改善激素脸
         * useTool : 建议配合超声波导入仪、综合仪使用
         * useLocation : 脸部
         * status : 1
         * pics : [{"url":"http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/26/logo.png"}]
         * videos : []
         */

        public int id;
        public int titleId;
        public String title;
        public String content;
        public String suitCrowds;
        public String methods;
        public String price;
        public String needTime;
        public String advantage;
        public String fangShi;
        public String attention;
        public String functionName;
        public String useTool;
        public String useLocation;
        public int status;
        public List<PicsBean> pics;
        public List<PicsBean> videos;

        public static class PicsBean implements Parcelable {
            /**
             * url : http://dev-bucket-wmtc.oss-cn-qingdao.aliyuncs.com/uploadAttach/26/logo.png
             */

            public String url;
            public String type;

            protected PicsBean(Parcel in) {
                url = in.readString();
                type = in.readString();
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(url);
                dest.writeString(type);
            }
        }
    }
}
