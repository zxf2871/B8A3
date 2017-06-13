package com.study.b8a3.login.source.remote;

import android.support.annotation.NonNull;

import com.study.b8a3.Constants;
import com.study.b8a3.common.ErrorCode;
import com.study.b8a3.login.User;
import com.study.b8a3.login.source.LoginDataSource;
import com.study.b8a3.main.B8a3Application;
import com.study.b8a3.network.NetworkUpdateHandler;
import com.study.b8a3.thread.ThreadServices;

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
    private String content = "";
    private String message = "";
    private User mUser;

    public static LoginRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoginRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void login(@NonNull final String userName, @NonNull String password, final LoginCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("username", userName);
        params.put("password", password);
        ThreadServices.getInstance().updateServices(new NetworkUpdateHandler(B8a3Application.getInstance(), Constants.LOGIN_URL, params) {

//返回的数据格式
//                {
//                    "code":200,
//                    "message":"用户登录成功",
//                    "content":{
//                                   "id":2,
//                                   "username":"admin1",
//                                   "image":"http://localhost:8080/ ",
//                                   "enabled":1
//                    }
//                }
            @Override
            protected int analyseResponse(String response) {

                int code = ErrorCode.ERROR_FORMAT;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    code = jsonObject.optInt("code", ErrorCode.ERROR_FORMAT);
                    message = jsonObject.optString("message", "");
                    if (code == ErrorCode.SUCCESS) {
                        content = jsonObject.optString("content");
                        mUser = User.paseToUser(content);
                        token = mUser.getToken();
                        return code;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    return code;
                }
                return code;
            }

            @Override
            protected boolean success(String response) {
                callback.onLoginSuccess(token, content);
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
    public void saveLogin(@NonNull String token, String userString) {

    }

    @Override
    public User getUser() {
        return mUser;
    }


}
