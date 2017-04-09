package com.study.b8a3.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;

public class LayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, LayoutActivity.class);
        context.startActivity(intent);
    }
}
