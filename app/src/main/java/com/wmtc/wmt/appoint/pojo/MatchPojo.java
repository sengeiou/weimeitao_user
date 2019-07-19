package com.wmtc.wmt.appoint.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Obl on 2019/5/17.
 * com.wmtc.wmt.appoint.pojo
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class MatchPojo implements Parcelable {
    public String longlat;
    public String prices;
    public String hufuxuKeys;
    public String xiangmuKeys;
    public String page;

    public MatchPojo() {
    }

    protected MatchPojo(Parcel in) {
        longlat = in.readString();
        prices = in.readString();
        hufuxuKeys = in.readString();
        xiangmuKeys = in.readString();
        page = in.readString();
    }

    public static final Creator<MatchPojo> CREATOR = new Creator<MatchPojo>() {
        @Override
        public MatchPojo createFromParcel(Parcel in) {
            return new MatchPojo(in);
        }

        @Override
        public MatchPojo[] newArray(int size) {
            return new MatchPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(longlat);
        dest.writeString(prices);
        dest.writeString(hufuxuKeys);
        dest.writeString(xiangmuKeys);
        dest.writeString(page);
    }
}
