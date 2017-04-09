package com.study.b8a3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.study.b8a3.R;

/**
 * Created by B8A3 on 2017/4/7.
 */

public class FirstActivity extends BaseActivity {
    public static String TAG = ThirdActivity.class.getSimpleName();

    private Button mButtonSuccess;
    private Button mButtonError;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ;
        setContentView(R.layout.first_activity);
        Intent intent = getIntent();
        ((TextView)findViewById(R.id.data_show)).setText(intent.getStringExtra("data"));
        mButtonSuccess = (Button)findViewById(R.id.button_sucess);
        mButtonSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.putExtra("param", ((EditText)findViewById(R.id.edit_param)).getText().toString());
                setResult(RESULT_OK, intent1);
                finish();
            }
        });

        mButtonError = (Button)findViewById(R.id.button_error);
        mButtonError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.putExtra("param", ((EditText)findViewById(R.id.edit_param)).getText().toString());
                setResult(RESULT_CANCELED, intent1);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent();
        intent1.putExtra("param", "摁下了返回键");
        setResult(RESULT_CANCELED, intent1);
        super.onBackPressed();
        finish();

    }
}
