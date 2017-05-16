package com.study.b8a3.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
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
        this(context, null);
    }

    public SimmerButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimmerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        repeatCount = DEFAULT_REPEAT_COUNT;
        duration = DEFAULT_DURATION;
        startDelay = DEFAULT_START_DELAY;
        direction = DEFAULT_DIRECTION;
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
}
