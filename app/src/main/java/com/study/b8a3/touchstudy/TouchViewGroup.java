package com.study.b8a3.touchstudy;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/20.
 */

public class TouchViewGroup extends LinearLayout {
    public static String TAG = TouchViewGroup.class.getSimpleName();


    public TouchViewGroup(Context context) {
        super(context, null);
    }

    public TouchViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public TouchViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("touch==========", ": dispatchTouchEvent ");
        return super.dispatchTouchEvent(event);
//        return true;
//        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("touch==========", ": onInterceptTouchEvent ");
        }
        return super.onInterceptTouchEvent(ev);
//        return true;
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("touch==========", ": onTouchEvent ");
        }
//        return super.onTouchEvent(event);
        return true;
//        return false;
    }




}
