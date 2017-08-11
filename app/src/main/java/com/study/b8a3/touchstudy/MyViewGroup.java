package com.study.b8a3.touchstudy;

import android.content.Context;
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

/**
 * Created by Administrator on 2017/6/20.transact
 */

public class MyViewGroup extends ViewGroup {
    public static String TAG = MyViewGroup.class.getSimpleName();

    //滚动计算辅助类
    private Scroller mScroller;
    //手指落点的X坐标
    private float mLastMotionX = 0;
    //屏幕宽度
    private int screenWidth;
    //手指加速度辅助类
    private VelocityTracker mVelocityTracker;
    //每秒移动的最小dp
    private int mMinimumVelocity;
    //每秒移动的最大dp
    private int mMaximumVelocity;


    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        //获取屏幕宽度
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        //获取最小和最大的移动距离
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();


    }


    @Override
    public void computeScroll() {
        //判断滚动时候停止
        if (mScroller.computeScrollOffset()) {
            //滚动到指定的位置
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //这句话必须写，否则不能实时刷新
            postInvalidate();
        }
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重新设置宽高
        this.setMeasuredDimension(measureWidth(widthMeasureSpec, heightMeasureSpec)
                , measureHeight(widthMeasureSpec, heightMeasureSpec));
    }


    /**
     * 测量高度
     */
    private int measureHeight(int widthMeasureSpec, int heightMeasureSpec) {
        //高度
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //父控件的高（wrap_content）
        int height = 0;
        int childCount = getChildCount();

        //重新测量子view的宽度，以及最大高度
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            height += childHeight;
        }
        height = height / childCount;
        return modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height;
    }


    /**
     * 测量宽度
     */
    private int measureWidth(int widthMeasureSpec, int heightMeasureSpec) {
        // 宽度
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        //父控件的宽（wrap_content）
        int width = 0;
        int childCount = getChildCount();

        //重新测量子view的宽度，以及最大高度
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            width += childWidth;
        }
        return modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;//子View左边的间距
        int childWidth;//子View的宽度
        int height = getHeight();//屏幕的宽度
        int childCount = getChildCount();//子View的数量
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            child.layout(childLeft, 0, childLeft + childWidth, height);
            childLeft += childWidth;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        int action = event.getAction();
        //获取现在手指所在的位置的x坐标
        float x = event.getX();
        //判断触发的时间
        switch (action) {
            //按下事件
            case MotionEvent.ACTION_DOWN:
                //初始化或服用加速度测试器
                initOrResetVelocityTracker();
                //测试器添加按下事件
                mVelocityTracker.addMovement(event);
                //如果停止滚动则取消动画（即手指按下就停止滚动）
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                //获取现在的x坐标
                mLastMotionX = event.getX();
                break;
            //移动事件
            case MotionEvent.ACTION_MOVE:
                //测试器添加移动事件
                if (mVelocityTracker != null) {
                    mVelocityTracker.addMovement(event);
                }
                //计算移动的偏移量
                float delt = mLastMotionX - x;
                //重置手指位置
                mLastMotionX = x;
                //滚动
                scrollBy((int) delt, 0);
                break;
            //手指抬起事件
            case MotionEvent.ACTION_UP:
                //测试器添加抬起事件
                mVelocityTracker.addMovement(event);
                //添加加速度的测试时间，这里是测量1000毫秒内的加速度
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                //获取x方向加速度
                float pxsec = mVelocityTracker.getXVelocity();
                //得到最后一个子View
                View lastChild = getChildAt(getChildCount() - 1);
                //获取滑动的最大滑动距离（最后一个Child的右边框的坐标减去屏幕的宽度）
                int finalyChild = (int) (lastChild.getX() + lastChild.getWidth() - screenWidth);
                //如果x的加速度大于系统设定的最小移动距离，就可以惯性滑动
                if (Math.abs(pxsec) > mMinimumVelocity)
                    mScroller.fling(getScrollX(), 0, (int) -pxsec, 0, 0, finalyChild, 0, 0);
                //如果滑动的距离小于第一个控件的最左边（0）则回弹至（0,0）点
                if (getScrollX() < 0) {
                    scrollTo(0, 0);
                }
                //如果滑动的距离大于最大可滑动距离则滑动到最后一个子View
                if (getScrollX() >= finalyChild)
                    scrollTo(finalyChild, 0);
                //刷新界面
                invalidate();
                //清空测试器
                recycleVelocityTracker();
                break;
            default:
                break;
        }

        return true;



//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            Log.e("touch==========", ": onTouchEvent ");
//        }
//        return super.onTouchEvent(event);
//        return true;
//        return false;
    }

    /**
     * 创建或复用加速度测试器
     */
    private void initOrResetVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
    }

    /**
     * 回收加速度测试器，防止内存泄漏
     */
    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    public MyViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyViewGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new ViewGroup.LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MyViewGroup.LayoutParams;
    }


    @Override
    protected MyViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new MyViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
