package com.study.b8a3.aidltest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.study.b8a3.aidl.IBookManager;
import com.study.b8a3.main.BaseActivity;
import com.study.b8a3.R;

public class AIDLActivity extends BaseActivity {

    TaskService mTaskService;
    private IBookManager manager;
    ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidltest);
    }

    public void startBookServer(View view) {
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                manager = IBookManager.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
//        bindService(new Intent(this, TaskService.class),conn, BIND_AUTO_CREATE);

        //android5.0以后必须xian'shi
        Intent intent = new Intent("com.study.b8a3.aidltest.BOOK_SERVICE");
        intent.setPackage("com.study.b8a3");
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    public void getBook(View view) {
        if(manager == null){
            ((TextView)view).setText("什么也没有");
            return;
        }
        try {
           String bookName = manager.getBook(0).getBookString();
            Log.e(TAG, bookName);
            ((TextView)view).setText(bookName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void endBookServer(View view) {
        if(conn != null) {
            unbindService(conn);
            conn = null;
            manager = null;
        }
    }
}
