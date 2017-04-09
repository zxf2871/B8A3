package com.study.b8a3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.study.b8a3.R;

public class ThirdActivity extends BaseActivity {

    public static String TAG = ThirdActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ;

        setContentView(R.layout.activity_third);
    }
}
