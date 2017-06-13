package com.study.b8a3.login;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2017/3/1.
 */

public class User {
    private int id;
    private String userName;
    private String password;
    private String name;
    private String img;
    private String token;
    private List<String> authorities;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "";
    }

    public static User paseToUser(String userString) {

        User user = null;
        if (!TextUtils.isEmpty(userString)) {
            user = new User();
            try {
                JSONObject jsonObject = new JSONObject(userString);
                user.setId(jsonObject.optInt("id"));
                user.setName(jsonObject.optString("name"));
                user.setImg(jsonObject.optString("image"));
                user.setToken(jsonObject.optString("token"));
                user.setUserName(jsonObject.optString("username"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return user;
    }

}
