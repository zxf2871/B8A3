package com.study.b8a3.animation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.study.b8a3.FloatingWindowActivity;
import com.study.b8a3.R;
import com.study.b8a3.animation.property.PropertyActivity;
import com.study.b8a3.main.BaseActivity;

public class AnimationActivity extends BaseActivity implements View.OnClickListener {
    private Animation mRotateAnimation;
    private Animation mLineMoveAnimation;

    private View mSearchSmall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animation);


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

        mRotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        findViewById(R.id.img_search).setAnimation(mRotateAnimation);
        LinearInterpolator lin = new LinearInterpolator();
        mRotateAnimation.setInterpolator(lin);

        mLineMoveAnimation = AnimationUtils.loadAnimation(this, R.anim.line_anim);
        mSearchSmall = findViewById(R.id.img_search_small);
        mSearchSmall.setAnimation(mLineMoveAnimation);
    }

    public void startAnimFloatingWindowActivity(View view) {
        startActivity(new Intent(this, FloatingWindowActivity.class));
        mRotateAnimation.start();
    }

    public void startPropertyAnim(View view) {
        PropertyActivity.startPropertyActivity(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.AccelerateDecelerate:
                break;
            case R.id.Accelerate:
                break;
            case R.id.Decelerate:
                break;
            case R.id.Bounce:
                break;
            case R.id.Anticipate:
                break;
            case R.id.AnticipateOvershoot:
                break;
            case R.id.Cycle:
                break;
            case R.id.Linear:
                break;
            case R.id.Overshoot:
                break;
            case R.id.Nomal:
                break;
        }
    }
}
