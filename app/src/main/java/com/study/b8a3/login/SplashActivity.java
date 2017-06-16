package com.study.b8a3.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.study.b8a3.R;
import com.study.b8a3.activity.HomeActivity;
import com.study.b8a3.main.BaseActivity;
import com.study.b8a3.utils.Injection;

public class SplashActivity extends BaseActivity implements LoginContract.View{
    private LoginContract.Presenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LoginPresenter presenter = new LoginPresenter(this, Injection.provideTasksRepository(getApplicationContext()));
        this.setPresenter(presenter);
        presenter.login("","");
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, SplashActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mLoginPresenter  = presenter;
    }

    @Override
    public void loginSuccess() {
        HomeActivity.startActivity(this);
        finish();
    }

    @Override
    public void loginError(String message) {
        LoginActivity.startActivity(this);
        finish();
    }

    @Override
    public void loginOutSuccess() {

    }
}
