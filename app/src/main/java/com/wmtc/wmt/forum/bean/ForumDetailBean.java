package com.wmtc.wmt.forum.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.wmtc.wmt.base.BaseBean;

import java.util.List;

/**
 * Created by Obl on 2019/5/3.
 * com.wmtc.wmt.mvp.bean
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */
public class ForumDetailBean extends BaseBean {

    /**
     * data : {"forumId":"324510279306051584","forumType":"1","subitemId":"324510274801369088","pictures":["xinde/c27dde71f59527790094e9e731dd92df.png","xinde/67a0928ef4698ea9ab6a1bc6074a534d.png","xinde/e4f3cd61c5a1713afdc6467a8734330a.png","xinde/9a8f390c25e5bce01d5a582b03c764d4.png","xinde/7009fd7040d4654a60e69149936d1f3f.png","xinde/6815380e39e96e6c1f4255749b311462.png"],"content":"按摩皮肤真的有效哦","topicList":[],"createTime":"2019-4-15 11:27:59","createUser":"淡淡的额","createUserId":"323471596649971712","createUserAvatar":"avatar/201904120f4d4a6d-f36a-46f9-8e80-b5c7ba4b75e0-photo.jpg","birthday":"1993-1-01 12:00:00","fuzhiName":"油性皮肤","attentionUserStatus":"-1","dianzianNum":"5","shoucangNum":"0","dianzanStatus":"-1","shoucangStatus":"-1"}
     */

    public DataBean data;


    public static class DataBean implements Parcelable {

        /**
         * forumId : 331073071286845440
         * forumType : 1
         * subitemId : 331073069898530816
         * pictures : ["xinde/395622cb5a118133fd9e640a3d7175ef.png"]
         * content : 我是有没有话题和位置，但是也很长，试试标题够不够长呀，够了吗
         * topicList : ["可以去黑头的美容仪"]
         * positionName : 创客公寓
         * createTime : 2019-5-03 02:06:11
         * createUser : 袁鹏
         * createUserId : 328488790869934080
         * createUserAvatar : avatar/201904286676f001-ad6e-4f00-90e0-2bdceaca7dca-photo.jpg
         * birthday : 1983-1-01 12:00:00
         * fuzhiName : 油性皮肤
         * attentionUserStatus : -1
         * dianzianNum : 1
         * shoucangNum : 0
         * dianzanStatus : -1
         * shoucangStatus : -1
         */

        public String forumId;
        public String forumType;
        public String title;
        public String subitemId;
        public String content;
        public String positionName;
        public String createTime;
        public String createUser;
        public String createUserId;
        public String createUserAvatar;
        public String birthday;
        public String fuzhiName;
        public String attentionUserStatus;
        public String dianzianNum;
        public String shoucangNum;
        public String dianzanStatus;
        public String shoucangStatus;
        public List<String> pictures;
        public List<String> topicList;
        public String topicListStr;
        public String status;

        protected DataBean(Parcel in) {
            forumId = in.readString();
            forumType = in.readString();
            title = in.readString();
            subitemId = in.readString();
            content = in.readString();
            positionName = in.readString();
            createTime = in.readString();
            createUser = in.readString();
            createUserId = in.readString();
            createUserAvatar = in.readString();
            birthday = in.readString();
            fuzhiName = in.readString();
            attentionUserStatus = in.readString();
            dianzianNum = in.readString();
            shoucangNum = in.readString();
            dianzanStatus = in.readString();
            shoucangStatus = in.readString();
            pictures = in.createStringArrayList();
            topicList = in.createStringArrayList();
            topicListStr = in.readString();
            status = in.readString();
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
            dest.writeString(forumId);
            dest.writeString(forumType);
            dest.writeString(title);
            dest.writeString(subitemId);
            dest.writeString(content);
            dest.writeString(positionName);
            dest.writeString(createTime);
            dest.writeString(createUser);
            dest.writeString(createUserId);
            dest.writeString(createUserAvatar);
            dest.writeString(birthday);
            dest.writeString(fuzhiName);
            dest.writeString(attentionUserStatus);
            dest.writeString(dianzianNum);
            dest.writeString(shoucangNum);
            dest.writeString(dianzanStatus);
            dest.writeString(shoucangStatus);
            dest.writeStringList(pictures);
            dest.writeStringList(topicList);
            dest.writeString(topicListStr);
            dest.writeString(status);
        }
    }


}
