package com.study.b8a3.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;

/**
 * Created by Administrator on 2017/5/23.
 */

public class ShimmerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shimmer);
    }

    public static void startShimmerActivity(Context context) {
        Intent intent = new Intent(context, ShimmerActivity.class);
        context.startActivity(intent);
    }

}
