package com.study.b8a3.touchstudy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;

public class TouchActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
    }

    public static void startTouchActivity(Context context) {
        Intent intent = new Intent(context, TouchActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("touch----------", ": dispatchTouchEvent");
        }
        return super.dispatchTouchEvent(ev);
//        return false
//        return true
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("touch----------", ": onTouchEvent");
        }
//        return super.onTouchEvent(event)
//        return false
        return true;
    }
}