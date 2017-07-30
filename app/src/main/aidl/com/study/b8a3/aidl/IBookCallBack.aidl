// IBookCallBack.aidl
package com.study.b8a3.aidl;
import com.study.b8a3.aidl.Book;

// Declare any non-default types here with import statements

interface IBookCallBack {

    void getBoolSuccess(String book);
    void getBoolFail(String message);
}
