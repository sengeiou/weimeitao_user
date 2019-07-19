package com.wmtc.chatmodule.enity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：Rance on 2016/12/21 16:22
 * 邮箱：rance935@163.com
 */
public class ImageInfo implements Parcelable {
    private int locationX;
    private int locationY;
    private int width;
    private int height;
    private String imageUrl;

    public ImageInfo() {
    }

    protected ImageInfo(Parcel in) {
        locationX = in.readInt();
        locationY = in.readInt();
        width = in.readInt();
        height = in.readInt();
        imageUrl = in.readString();
    }

    public static final Creator<ImageInfo> CREATOR = new Creator<ImageInfo>() {
        @Override
        public ImageInfo createFromParcel(Parcel in) {
            return new ImageInfo(in);
        }

        @Override
        public ImageInfo[] newArray(int size) {
            return new ImageInfo[size];
        }
    };

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(locationX);
        dest.writeInt(locationY);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(imageUrl);
    }
}
