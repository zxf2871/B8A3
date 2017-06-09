package com.study.b8a3.login.source.remote;

import android.support.annotation.NonNull;

import com.study.b8a3.Constants;
import com.study.b8a3.login.source.LoginDataSource;
import com.study.b8a3.main.B8a3Application;
import com.study.b8a3.network.NetworkUpdateHandler;
import com.study.b8a3.thread.ThreadServices;
import com.study.b8a3.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/1.
 */

public class LoginRemoteDataSource implements LoginDataSource {
    private static LoginRemoteDataSource INSTANCE;

    public static LoginRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoginRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void login(@NonNull String userName, @NonNull String password, LoginCallback callback) {
        Map<String,String> params = new HashMap<>();
        params.put("username",userName);
        params.put("password",password);
        ThreadServices.getInstance().updateServices(new NetworkUpdateHandler(B8a3Application.getInstance(), Constants.LOGIN_URL, params) {

            @Override
            protected boolean analyseResponse(String response) {
                return false;
            }

            @Override
            protected boolean success(String response) {
                return false;
            }

            @Override
            protected boolean fail(int code) {
                return false;
            }
        });
    }

    @Override
    public void saveLogin(@NonNull String token) {
        SharedPreferencesUtils.put(Constants.LOGIN_USER, token);
    }
}
