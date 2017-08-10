package com.study.b8a3.touchstudy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.study.b8a3.R;

/**
 * Created by B8A3 on 2017/8/9.
 */

public class MyView extends View {
    private String mMyText;
    private int mMyTextColor;
    private int mMyTextSize;
    private int mMyBackground;
    private int mMyTextcolorAnother;

    private Paint mPaint;
    private Rect mBound;


    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        /**
         * 写在declare-styleable里面
         */
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.MyView_my_text:
                    mMyText = array.getString(attr);
                    break;
                case R.styleable.MyView_my_text_color:
                    mMyTextColor = array.getColor(attr, Color.BLACK);
                    break;

                case R.styleable.MyView_my_background_color:
                    mMyBackground = array.getColor(attr, Color.TRANSPARENT);
                    break;

                case R.styleable.MyView_my_text_size:
                    mMyTextSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }

        }
        array.recycle();

        /**
         * 写在declare-styleable外面需要使用一个id数组
         */
        TypedArray ta = context.obtainStyledAttributes(attrs, new int[]{R.attr.my_text_color_another});
        mMyTextcolorAnother = ta.getColor(0,0);
        ta.recycle();

        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mMyTextSize);
        // mPaint.setColor(mTitleTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(mMyText, 0, mMyText.length(), mBound);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mMyBackground);
        canvas.drawRect(0, 0, getMeasuredWidth() / 2, getMeasuredHeight() / 3, mPaint);

        mPaint.setColor(mMyTextcolorAnother);
        canvas.drawRect(getMeasuredWidth() / 2, getMeasuredHeight() / 3, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mMyTextColor);
        canvas.drawText(mMyText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }


}
