package com.wmtc.wmt.personal.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/18.
 * com.wmtc.wmt.personal.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class LocalListBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean implements Parcelable {
        /**
         * id : 336517847003955200
         * userId : 327499832975425536
         * province : 山东省
         * city : 青岛市
         * area : 市南区
         * address : 词句均码
         * deliveryName : 超级参谋参谋
         * deliveryTel : 466868989
         * isDefault : 1
         * status : 1
         * createby : 327499832975425536
         * createtime : 2019-05-18T06:41:47.000+0000
         * updateby : 327499832975425536
         * updatetime : 2019-05-18T06:41:47.000+0000
         */

        public long id;
        public long userId;
        public String province;
        public String city;
        public String area;
        public String address;
        public String deliveryName;
        public String deliveryTel;
        public int isDefault;
        public int status;
        public long createby;
        public String createtime;
        public long updateby;
        public String updatetime;

        protected DataBean(Parcel in) {
            id = in.readLong();
            userId = in.readLong();
            province = in.readString();
            city = in.readString();
            area = in.readString();
            address = in.readString();
            deliveryName = in.readString();
            deliveryTel = in.readString();
            isDefault = in.readInt();
            status = in.readInt();
            createby = in.readLong();
            createtime = in.readString();
            updateby = in.readLong();
            updatetime = in.readString();
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
            dest.writeLong(id);
            dest.writeLong(userId);
            dest.writeString(province);
            dest.writeString(city);
            dest.writeString(area);
            dest.writeString(address);
            dest.writeString(deliveryName);
            dest.writeString(deliveryTel);
            dest.writeInt(isDefault);
            dest.writeInt(status);
            dest.writeLong(createby);
            dest.writeString(createtime);
            dest.writeLong(updateby);
            dest.writeString(updatetime);
        }
    }
}
