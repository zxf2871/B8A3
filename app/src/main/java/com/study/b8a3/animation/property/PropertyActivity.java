package com.study.b8a3.animation.property;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;

/**
 * Created by Administrator on 2017/6/29.
 */

public class PropertyActivity extends BaseActivity {

    private AnimatorSet set;
    private Button mButton;

    public static void startPropertyActivity(Context context) {
        context.startActivity(new Intent(context, PropertyActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_property);
        set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.property_anim);
        mButton = (Button)findViewById(R.id.anim_button);

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
        AnimatorSet set1 = new AnimatorSet();
        set1.setTarget(view);
        set1.playTogether(
                ObjectAnimator.ofFloat(view, "rotationX", 0, 360),
                ObjectAnimator.ofFloat(view, "rotationY", 0, 180),
                ObjectAnimator.ofFloat(view, "rotation", 0, -90),
                ObjectAnimator.ofFloat(view, "translationX", 0, 90),
                ObjectAnimator.ofFloat(view, "translationY", 0, 90),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.5f),
                ObjectAnimator.ofFloat(view, "scaleY", 1, 0.5f),
                ObjectAnimator.ofFloat(view, "alpha", 1,0.25f, 1)
        );
        set1.setDuration(5*1000);
        set1.start();
    }
}
