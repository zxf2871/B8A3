package com.study.b8a3.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;

public class LayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("锁屏状态：","onCreate");

        setContentView(R.layout.activity_layout);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("锁屏状态：","onDestroy");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("锁屏状态：","onStart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("锁屏状态：","onStop");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("锁屏状态：","onRestart");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("锁屏状态：","onPause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("锁屏状态：","onResume");

    }
}
