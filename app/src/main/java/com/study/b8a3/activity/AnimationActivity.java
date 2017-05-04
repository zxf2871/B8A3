package com.study.b8a3.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;

import com.study.b8a3.R;
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

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AnimationActivity.class);
        context.startActivity(intent);

    }

    public void startAnim(View view) {

        mRotateAnimation.start();
    }
}
