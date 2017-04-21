package com.study.b8a3.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.b8a3.FloatingWindowActivity;
import com.study.b8a3.R;
import com.study.b8a3.main.B8a3Application;
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
        startActivity(new Intent(this, FloatingWindowActivity.class));

        mRotateAnimation.start();
    }
}
