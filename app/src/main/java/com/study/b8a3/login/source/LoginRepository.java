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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
public class LoginRepository implements LoginDataSource {

    private static LoginRepository INSTANCE = null;

    private final LoginDataSource mUsersRemoteDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    User mCachedUser;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private LoginRepository(@NonNull LoginDataSource tasksRemoteDataSource) {
        mUsersRemoteDataSource = checkNotNull(tasksRemoteDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param tasksRemoteDataSource the backend data source
     * @return the {@link LoginRepository} instance
     */
    public static LoginRepository getInstance(LoginDataSource tasksRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new LoginRepository(tasksRemoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(LoginDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }



    /**
     * Gets tasks from local data source (sqlite) unless the table is new or empty. In that case it
     * uses the network data source. This is done to simplify the sample.
     * <p>
     * Note: {@link LoginCallback#()} is fired if both data sources fail to
     * get the data.
     */
    @Override
    public void login(@NonNull final String userName, @NonNull String password, @NonNull final LoginCallback callback) {
        checkNotNull(userName);
        checkNotNull(password);
        checkNotNull(callback);

        User cachedUser = mCachedUser;

        // Respond immediately with cache if available
        if (cachedUser != null) {
            callback.onLoginSuccess(cachedUser.getToken());
            return;
        }

        mUsersRemoteDataSource.login(userName, password, callback);

    }

    @Override
    public void saveLogin(@NonNull String token) {
        mUsersRemoteDataSource.saveLogin(token);
    }
}
