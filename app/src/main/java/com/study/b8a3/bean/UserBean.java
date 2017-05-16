package com.study.b8a3.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by B8A3 on 2017/5/7.
 */

public class UserBean implements Parcelable {

    public int useId;
    public String useName;


    protected UserBean(Parcel in) {
        useId = in.readInt();
        useName = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
