package com.study.b8a3.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

/**
 * Created by Administrator on 2017/5/16.
 */

public class SimmerButton extends Button {

    private static final int DEFAULT_REPEAT_COUNT = ValueAnimator.INFINITE;
    private static final long DEFAULT_DURATION = 1000;
    private static final long DEFAULT_START_DELAY = 0;
    private static final int DEFAULT_DIRECTION = 0;

    private ObjectAnimator animator;
    private int repeatCount;
    private long duration;
    private long startDelay;
    private int direction;
    private Animator.AnimatorListener animatorListener;

    private boolean isShimmering;


    public SimmerButton(Context context) {
        super(context);
        repeatCount = DEFAULT_REPEAT_COUNT;
        duration = DEFAULT_DURATION;
        startDelay = DEFAULT_START_DELAY;
        direction = DEFAULT_DIRECTION;
    }

    public SimmerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimmerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void startSimmer() {

        if (isAnimating()) {
            return;
        }

        final Runnable animate = new Runnable() {
            @Override
            public void run() {

                isShimmering = true;

                float fromX = 0;
                float toX = SimmerButton.this.getWidth();
                if (direction == 0) {
                    fromX = SimmerButton.this.getWidth();
                    toX = 0;
                }

                animator = ObjectAnimator.ofFloat(SimmerButton.this, "gradientX", fromX, toX);
                animator.setRepeatCount(repeatCount);
                animator.setRepeatMode(ValueAnimator.RESTART);
                animator.setDuration(duration);
                animator.setStartDelay(startDelay);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Log.e(">>>>>>>>", "simmer start");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isShimmering = false;

                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            SimmerButton.this.postInvalidate();
                        } else {
                            SimmerButton.this.postInvalidateOnAnimation();
                        }

                        animator = null;
                        Log.e(">>>>>>>>", "simmer end");

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        Log.e(">>>>>>>>", "simmer cancel");

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        Log.e(">>>>>>>>", "simmer repeat");

                    }
                });

                if (animatorListener != null) {
                    animator.addListener(animatorListener);
                }

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
    private Paint paint;
    @Override
    protected void onDraw(Canvas canvas) {
        if(paint == null){
            paint = getPaint();
        }
        // only draw the shader gradient over the text while animating
        if (isShimmering) {

//            // first onDraw() when shimmering
//            if (paint.getShader() == null) {
//                paint.setShader(linearGradient);
//            }
//
//            // translate the shader local matrix
//            linearGradientMatrix.setTranslate(2 * gradientX, 0);
//
//            // this is required in order to invalidate the shader's position
//            linearGradient.setLocalMatrix(linearGradientMatrix);

            Log.e(">>>>>>>>", "simmer ondraw");


        } else {
            // we're not animating, remove the shader from the paint
            paint.setShader(null);
        }

        super.onDraw(canvas);

    }
}
