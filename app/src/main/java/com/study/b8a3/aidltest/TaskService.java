package com.study.b8a3.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.study.b8a3.aidl.Book;
import com.study.b8a3.aidl.IBookManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */

public class TaskService extends Service {
    private static final String TAG = "TaskService";

    @Override
    public void onCreate() {
        printf("service create");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        printf("service start id=" + startId);
//        callback(startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent t) {
        printf("service on bind");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        printf("service on destroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        printf("service on unbind");
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent) {
        printf("service on rebind");
        super.onRebind(intent);
    }

    private void printf(String str) {
        Log.v(TAG, "###################------ " + str + "------");
    }


    private final IBookManager.Stub mBinder = new IBookManager.Stub() {

        @Override
        public Book getBook(int i) throws RemoteException {

            return new Book("Book");
        }

        @Override
        public String addBook(Book book) throws RemoteException {
            return book.getBookString();
        }
    };


}
