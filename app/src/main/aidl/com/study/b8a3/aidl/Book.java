package com.study.b8a3.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by B8A3 on 2017/5/7.
 */

public class Book implements Parcelable {

    private int bookId;
    private String bookString;

    protected Book(Parcel in) {
        bookId = in.readInt();
        bookString = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bookId);
        parcel.writeString(bookString);
    }
}
