package com.study.b8a3.touchstudy;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/20.
 */

public class TouchView extends android.support.v7.widget.AppCompatTextView {
    public static String TAG = TextView.class.getSimpleName();

    public TouchView(Context context) {
        super(context, null);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("touch≡≡≡≡≡", ": dispatchTouchEvent ");
        }
//        return super.dispatchTouchEvent(event);
//        return true;
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.e("touch≡≡≡≡≡", ": onTouchEvent ");
        }
//        return super.onTouchEvent(event);
        //        return true;
        return false;
    }
}
