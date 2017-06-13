package com.study.b8a3.login.source.local;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.study.b8a3.Constants;
import com.study.b8a3.common.ErrorCode;
import com.study.b8a3.login.User;
import com.study.b8a3.login.source.LoginDataSource;
import com.study.b8a3.main.B8a3Application;
import com.study.b8a3.network.NetworkUpdateHandler;
import com.study.b8a3.thread.ThreadServices;
import com.study.b8a3.thread.UpdateHandler;
import com.study.b8a3.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/1.
 */

public class LoginLocalDataSource implements LoginDataSource {
    private static LoginLocalDataSource INSTANCE;
    private String token = "";
    private String content = "";
    private String message = "";
    private User mUser;

    public static LoginLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoginLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void login(@NonNull final String userName, @NonNull String password, final LoginCallback callback) {
        ThreadServices.getInstance().updateServices(new UpdateHandler(B8a3Application.getInstance()) {
            @Override
            public boolean doUpdateNow() {
                String token = (String) SharedPreferencesUtils.get(Constants.TOKEN, "");
                return !TextUtils.isEmpty(token);
            }

            @Override
            public void doUpdateSuccess() {
                callback.onLoginSuccess(token, content);
            }

            @Override
            public void doUpdateFail() {
                callback.onLoginError(ErrorCode.ERROR_NO_TOKEN, message);
            }
        });
    }

    @Override
    public void saveLogin(@NonNull String token, String userString) {
        SharedPreferencesUtils.put(Constants.TOKEN, token);
        SharedPreferencesUtils.put(Constants.LOGIN_USER, userString);
    }

    @Override
    public User getUser() {
        return mUser;
    }


}
