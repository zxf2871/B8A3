package com.study.b8a3.touchstudy;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.bumptech.glide.load.data.mediastore.MediaStoreUtil;
import com.study.b8a3.R;
import com.study.b8a3.login.LoginContract;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/6/20.transact
 */

public class MyViewGroup extends ViewGroup {
    public static String TAG = MyViewGroup.class.getSimpleName();

    private int mSpace = 0;

    //速度最终
    private VelocityTracker mVelocityTracker;
    //滚动计算辅助类
    private Scroller mScroller;

    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.MyViewGroup, defStyleAttr, 0);
        mSpace = array.getDimensionPixelSize(R.styleable.MyViewGroup_space, 0);
        array.recycle();

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("touch==========", ": onTouch ");
                }
                return false;
            }
        });
        init(context);
    }

    private void init(Context context) {
        //初始化辅助类
        mScroller = new Scroller(context);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("touch==========", ": dispatchTouchEvent ");
        }
        return super.dispatchTouchEvent(event);
//        return true;
//        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("touch==========", ": onInterceptTouchEvent ");
        }
//        return super.onInterceptTouchEvent(ev);
        return true;
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(event);

        return super.onTouchEvent(event);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /**
         获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;

        int cCount = getChildCount();
        int cWith = 0;
        int cHeight = 0;

        MarginLayoutParams cParams = null;


        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            cWith = view.getMeasuredWidth();
            cHeight = view.getMeasuredHeight();
            cParams = (MarginLayoutParams) view.getLayoutParams();
            width = cWith + mSpace + cParams.leftMargin + cParams.rightMargin;
            height = cHeight + mSpace + cParams.topMargin + cParams.bottomMargin;
        }


        /**
         * 如果是wrap_content设置为我们计算的值
         * 否则：直接设置为父容器计算的值
         */
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int wAdd = 0;
        int hAdd = 0;

        int cWidth = 0;
        int cheight = 0;

        MarginLayoutParams cParams = null;

        wAdd = getPaddingLeft();
        hAdd = getPaddingRight();

        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            cWidth = view.getMeasuredWidth();
            cheight = view.getMeasuredHeight();

            cParams = (MarginLayoutParams) view.getLayoutParams();

            wAdd = wAdd + cParams.leftMargin;
            hAdd = hAdd + cParams.topMargin;
            view.layout(wAdd, hAdd, wAdd + cWidth, hAdd + cheight);
            wAdd = wAdd + mSpace;
            hAdd = hAdd + mSpace;
        }
    }




    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.clear();
        mVelocityTracker.recycle();
    }
}
