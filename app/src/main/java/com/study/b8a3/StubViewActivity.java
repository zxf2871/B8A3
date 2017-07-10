package com.study.b8a3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.study.b8a3.main.BaseActivity;

public class StubViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stub_view);
    }

//    public static void startActivity(Context context){
//        context.startActivity(new Intent(context, StubViewActivity.class));
//    }

}
