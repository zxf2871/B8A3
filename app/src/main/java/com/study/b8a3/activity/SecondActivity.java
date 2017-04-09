package com.study.b8a3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.study.b8a3.R;

/**
 * Created by B8A3 on 2017/4/8.
 */

public class SecondActivity extends AppCompatActivity {
    public static String TAG = SecondActivity.class.getSimpleName();


    private String saveString = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        Log.e("---------" + TAG, "onCreate");

        if (savedInstanceState != null) {
            saveString = savedInstanceState.getString("saved_data");
        }

        if (TextUtils.isEmpty(saveString)) {
            saveString = "保存, 初始化, onCreated";
        } else {
            Toast.makeText(this, "以前保存的: " + saveString, Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.btn_second_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("---------" + TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("---------" + TAG, "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("---------" + TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("---------" + TAG, "onPause");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("---------" + TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("---------" + TAG, "onRestart");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("saved_data", saveString);
    }

    public void openDialogActivity(View view) {
        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
    }

    public void openSingleInstanceActivity(View view) {
        startActivity(new Intent(SecondActivity.this, SingleInstanceActivity.class));
    }
}
