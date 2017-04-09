package com.study.b8a3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;

public class SingleInstanceActivity extends BaseActivity {
    public static String TAG = ThirdActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
    }

    public void openThirdActivity(View view) {
        startActivity(new Intent(SingleInstanceActivity.this, ThirdActivity.class));
    }
}
