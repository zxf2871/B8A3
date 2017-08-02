package com.study.b8a3.animation.property;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;

public class TweenActivity extends BaseActivity implements View.OnClickListener{

    private View mSearchSmall;
    private Animation mLineMoveAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween);


        findViewById(R.id.AccelerateDecelerate).setOnClickListener(this);
        findViewById(R.id.Accelerate).setOnClickListener(this);
        findViewById(R.id.Decelerate).setOnClickListener(this);
        findViewById(R.id.Bounce).setOnClickListener(this);
        findViewById(R.id.Anticipate).setOnClickListener(this);
        findViewById(R.id.AnticipateOvershoot).setOnClickListener(this);
        findViewById(R.id.Cycle).setOnClickListener(this);
        findViewById(R.id.Linear).setOnClickListener(this);
        findViewById(R.id.Overshoot).setOnClickListener(this);
        findViewById(R.id.Nomal).setOnClickListener(this);

        mLineMoveAnimation = AnimationUtils.loadAnimation(this, R.anim.line_anim);
        mSearchSmall = findViewById(R.id.img_search_small);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.AccelerateDecelerate:
                mLineMoveAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                break;
            case R.id.Accelerate:
                mLineMoveAnimation.setInterpolator(new AccelerateInterpolator());
                break;
            case R.id.Decelerate:
                mLineMoveAnimation.setInterpolator(new DecelerateInterpolator());
                break;
            case R.id.Bounce:
                mLineMoveAnimation.setInterpolator(new BounceInterpolator());
                break;
            case R.id.Anticipate:
                mLineMoveAnimation.setInterpolator(new AnticipateInterpolator());
                break;
            case R.id.AnticipateOvershoot:
                mLineMoveAnimation.setInterpolator(new AnticipateOvershootInterpolator());
                break;
            case R.id.Cycle:
                mLineMoveAnimation.setInterpolator(new CycleInterpolator(1f));
                break;
            case R.id.Linear:
                mLineMoveAnimation.setInterpolator(new LinearInterpolator());
                break;
            case R.id.Overshoot:
                mLineMoveAnimation.setInterpolator(new OvershootInterpolator());
                break;
            case R.id.Nomal:
                mLineMoveAnimation.setInterpolator(null);
                break;
        }
        mSearchSmall.startAnimation(mLineMoveAnimation);

    }

    @Override
    protected void onDestroy() {
        mLineMoveAnimation.cancel();
        super.onDestroy();
    }
}
