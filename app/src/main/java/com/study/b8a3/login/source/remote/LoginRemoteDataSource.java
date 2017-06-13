package com.study.b8a3.login.source.remote;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.study.b8a3.Constants;
import com.study.b8a3.login.source.LoginDataSource;
import com.study.b8a3.main.B8a3Application;
import com.study.b8a3.network.NetworkUpdateHandler;
import com.study.b8a3.thread.ThreadServices;
import com.study.b8a3.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/1.
 */

public class LoginRemoteDataSource implements LoginDataSource {
    private static LoginRemoteDataSource INSTANCE;
    private String token = "";
    private String message = "";

    public static LoginRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoginRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void login(@NonNull String userName, @NonNull String password, final LoginCallback callback) {
        Map<String,String> params = new HashMap<>();
        params.put("username",userName);
        params.put("password",password);
        ThreadServices.getInstance().updateServices(new NetworkUpdateHandler(B8a3Application.getInstance(), Constants.LOGIN_URL, params) {

            @Override
            protected boolean analyseResponse(String response) {
//                {
//                    "code":1,
//                    "message":"用户登录成功",
//                    "content":{
//                                   "id":2,
//                                   "username":"admin1",
//                                   "image":"http://localhost:8080/ ",
//                                   "enabled":1
//                    }
//                }


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.optInt("code", 0);
                    message = jsonObject.optString("message","");
                    if(code == 1){
                        token = jsonObject.optString("content");
                        return true;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            @Override
            protected boolean success(String response) {
                saveLogin(token);
                callback.onLoginSuccess(token);
                return true;
            }

            @Override
            protected boolean fail(int code) {
                callback.onLoginError(code, message);
                return false;
            }
        });
    }

    @Override
    public void saveLogin(@NonNull String token) {
        SharedPreferencesUtils.put(Constants.TOKEN, token);
        SharedPreferencesUtils.put(Constants.LOGIN_USER, token);
    }
}
