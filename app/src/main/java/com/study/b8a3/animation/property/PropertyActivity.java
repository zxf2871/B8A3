package com.study.b8a3.animation.property;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;

/**
 * Created by Administrator on 2017/6/29.
 */

public class PropertyActivity extends BaseActivity {

    private AnimatorSet set;

    public static void startPropertyActivity(Context context) {
        context.startActivity(new Intent(context, PropertyActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_property);
        set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.property_anim);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(set !=null){
            set.cancel();
            set = null;
        }
    }

    public void startAnim(View view) {
        set.setTarget(view);
        set.start();
    }
}
