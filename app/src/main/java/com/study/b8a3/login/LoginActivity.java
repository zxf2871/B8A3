package com.study.b8a3.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.study.b8a3.R;
import com.study.b8a3.main.B8a3Application;
import com.study.b8a3.main.BaseActivity;
import com.study.b8a3.utils.Injection;
import com.study.b8a3.activity.HomeActivity;


/**
 * Created by Administrator on 2017/3/1.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener{
    private LoginContract.Presenter mLoginPresenter;


    public static void startActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        LoginPresenter presenter = new LoginPresenter(this, Injection.provideTasksRepository(getApplicationContext()));
        this.setPresenter(presenter);
        findViewById(R.id.bt_login).setOnClickListener(this);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.mLoginPresenter = presenter;
    }


    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

    @Override
    public void loginError(final String message) {
        B8a3Application.getInstance().getGlobalHandler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bt_login){
            String userName = ((EditText) findViewById(R.id.et_username)).getText().toString();
            String password = ((EditText) findViewById(R.id.et_password)).getText().toString();
            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(this, R.string.user_erorr, Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, R.string.password_erorr, Toast.LENGTH_SHORT).show();
                return;
            }

            mLoginPresenter.login(userName, password);
        }
    }
}
