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
import android.widget.Button;

/**
 * Created by Administrator on 2017/5/16.
 */

/**
 * Created by Administrator on 2017/5/16.
 */

public class ShimmerButton extends Button {

    public static final String TAG = ShimmerButton.class.getSimpleName();
    private static final int DEFAULT_REPEAT_COUNT = ValueAnimator.INFINITE;
    private static final long DEFAULT_DURATION = 4000;
    private static final long DEFAULT_START_DELAY = 0;
    private static final int maskWith = 400;

    private ObjectAnimator animator;
    private int repeatCount;
    private long duration;
    private long startDelay;
    private float gradientX;
    private boolean isShimmering;
    private Paint mPaint;

    public ShimmerButton(Context context) {
        this(context, null);
    }

    public ShimmerButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShimmerButton(Context context, AttributeSet attrs, int defStyleAttr) {
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

        final Runnable animate = new Runnable() {
            @Override
            public void run() {
                isShimmering = true;
                float fromX = -maskWith;
                float toX = ShimmerButton.this.getWidth();
                animator = ObjectAnimator.ofFloat(ShimmerButton.this, "gradientX", fromX, toX);
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
                            ShimmerButton.this.postInvalidate();
                        } else {
                            ShimmerButton.this.postInvalidateOnAnimation();
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
        };
        animate.run();

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
        Shader mShader = new LinearGradient(gradientX, 0, gradientX + maskWith, getHeight(),
                new int[]{
                        Color.argb(0, 255, 255, 255),
                        Color.argb(255, 255, 255, 255),
                        Color.argb(255, 255, 255, 255),
                        Color.argb(0, 255, 255, 255)},
                new float[]{
                        0f,
                        0.25f,
                        0.5f,
                        0.75f,
                        1,
                }, Shader.TileMode.CLAMP); // 一个材质,打造出一个线性梯度沿著一条线。
        mPaint.setShader(mShader);
        canvas.drawRect(gradientX, 0, gradientX + maskWith, getHeight(), mPaint);// 正方形

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startSimmer();
    }
}
