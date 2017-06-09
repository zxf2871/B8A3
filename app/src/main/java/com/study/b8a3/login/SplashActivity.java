package com.study.b8a3.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.study.b8a3.Constants;
import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;
import com.study.b8a3.utils.SharedPreferencesUtils;
import com.study.b8a3.activity.HomeActivity;


public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (TextUtils.isEmpty((String) SharedPreferencesUtils.get(Constants.TOKEN, ""))) {
            LoginActivity.startActivity(this);
        } else {
            HomeActivity.startActivity(this);
        }
        finish();

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, SplashActivity.class);
        context.startActivity(intent);
    }
}
