package com.study.b8a3.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/5/23.
 */

public class ShimmerLayout extends LinearLayout {
    public static final String TAG = ShimmerLayout.class.getSimpleName();
    private static final int DEFAULT_REPEAT_COUNT = ValueAnimator.INFINITE;
    private static final long DEFAULT_DURATION = 5000;
    private static final long DEFAULT_START_DELAY = 0;
    private float maskWith;

    private ObjectAnimator animator;
    private int repeatCount;
    private long duration;
    private long startDelay;
    private float gradientX;
    private boolean isShimmering;
    private Paint mPaint;
    private float age = 0.35f;

    public ShimmerLayout(Context context) {
        this(context, null);
    }

    public ShimmerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShimmerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        repeatCount = DEFAULT_REPEAT_COUNT;
        duration = DEFAULT_DURATION;
        startDelay = DEFAULT_START_DELAY;
        mPaint = new Paint();
    }

    public void startSimmer() {

        if (isAnimating()) {
            return;
        }


        isShimmering = true;
        float fromX = 0;
        maskWith = ShimmerLayout.this.getWidth() / 2;
        maskWith = (1 + (1 / (2 - 4 * age))) * getHeight();
        float toX = ShimmerLayout.this.getWidth() + maskWith + getHeight();
        animator = ObjectAnimator.ofFloat(ShimmerLayout.this, "gradientX", fromX, toX);
        animator.setRepeatCount(repeatCount);
        animator.setDuration(duration);
        animator.setStartDelay(startDelay);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isShimmering = false;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    ShimmerLayout.this.postInvalidate();
                } else {
                    ShimmerLayout.this.postInvalidateOnAnimation();
                }
                animator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }

    public void cancel() {
        if (animator != null) {
            animator.cancel();
        }
    }

    public boolean isAnimating() {
        return animator != null && animator.isRunning();
    }

    //需要被keep
    public void setGradientX(float gradientX) {
        this.gradientX = gradientX;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Shader mShader = new LinearGradient(gradientX - maskWith - getHeight(), 0 - maskWith / 2, gradientX, getHeight() + maskWith / 2,
                new int[]{
                        Color.argb(0, 255, 255, 255),
                        Color.argb(200, 255, 255, 255),
                        Color.argb(200, 255, 255, 255),
                        Color.argb(0, 255, 255, 255)},
                new float[]{
                        age,
                        0.45f,
                        0.55f,
                        1 - age,
                }, Shader.TileMode.MIRROR); // 一个材质,打造出一个线性梯度沿著一条线。
        mPaint.setShader(mShader);
        canvas.drawRect(gradientX - maskWith - getHeight(), 0 - maskWith / 2, gradientX, getHeight() + maskWith / 2, mPaint);// 正方形

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.post(new Runnable() {
            @Override
            public void run() {
                startSimmer();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancel();
    }
}
