/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.study.b8a3.login.source;

import android.support.annotation.NonNull;

import com.study.b8a3.login.User;

public interface LoginDataSource {

    interface LoginCallback {

        void onLoginSuccess(String token, String userString);

        void onLoginError(int code, String message);

        void onLoginOutSuccess();
    }

    void login(@NonNull String userName, String password, LoginCallback callback);

    void loginOut(LoginCallback callback);

    void saveLogin(@NonNull String token, String userString);

    User getUser();
}
