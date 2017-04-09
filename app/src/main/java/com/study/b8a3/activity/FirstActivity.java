package com.study.b8a3.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;

import java.util.Random;

/**
 * Created by B8A3 on 2017/4/7.
 */

public class FirstActivity extends BaseActivity {
    public static String TAG = ThirdActivity.class.getSimpleName();

    private Button mButtonSuccess;
    private Button mButtonError;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mProgressBar = (ProgressBar)findViewById(R.id.pb_r);
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent();
        intent1.putExtra("param", "摁下了返回键");
        setResult(RESULT_CANCELED, intent1);
        super.onBackPressed();
        finish();

    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, FirstActivity.class);
//        intent.putExtra("data1", data);
        context.startActivity(intent);

    }

    public void changeProgressBarValueRandom(View view) {
        int v = (int) (Math.random()*101);
        mProgressBar.setProgress(v);
    }

    public void showAlertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FirstActivity.this);
        builder.setTitle("这是一个AlertDialog!");
        builder.setMessage("用来测试学习的Dialog");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(FirstActivity.this,"点击了确认",Toast.LENGTH_SHORT).show();
            }
        }); builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(FirstActivity.this,"点击了取消",Toast.LENGTH_SHORT).show();
            }
        });
        //设置不可点击其他地方同时点回退也不会取消
        builder.setCancelable(false);
        builder.show();
    }

    public void showProgressDialog(View view) {
        //ProgressDialog 没有按钮
        ProgressDialog dialog = new ProgressDialog(FirstActivity.this);
        dialog.setTitle("这是一个ProgressDialog用来学习的!");
        dialog.setMessage("加载中....");
        dialog.setCancelable(true);
        dialog.show();
    }
}
