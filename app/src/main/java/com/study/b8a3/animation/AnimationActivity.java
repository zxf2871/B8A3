package com.study.b8a3.animation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.study.b8a3.FloatingWindowActivity;
import com.study.b8a3.R;
import com.study.b8a3.animation.property.PropertyActivity;
import com.study.b8a3.main.BaseActivity;

public class AnimationActivity extends BaseActivity {
    private Animation mRotateAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animation);


        mRotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        findViewById(R.id.img_search).setAnimation(mRotateAnimation);
//        LinearInterpolator lin = new LinearInterpolator();
//        mRotateAnimation.setInterpolator(lin);

    }

    public void startAnimFloatingWindowActivity(View view) {
        startActivity(new Intent(this, FloatingWindowActivity.class));
        mRotateAnimation.start();
    }

    public void startPropertyAnim(View view) {
        PropertyActivity.startPropertyActivity(this);
    }
}
