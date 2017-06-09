// Copyright (c) 2016 The HAWK Inc. All rights reserved.
// Author: kingsonfan@hawk.com.

package com.study.b8a3.thread;

import android.content.Context;

public class  UpdateHandler {
    protected Context mContext = null;
    private long mDelay = 0L;

    public UpdateHandler(Context context) {
        mContext = context;
        init();
    }


    protected long delay() {
        return mDelay;
    }

    public void init() {
        // Init for feature.
    }

    // Check update for feature.
    public boolean checkUpdate() {
        return true;
    }

    public void initForUpdate() {
        // Init something for update.
    }

    public void doUpdateBefore() {
        // Do something before update.
    }

    public boolean doUpdateNow() {
        // Do update now.
        return true;  // default
    }

    public void doUpdateSuccess() {
        // Success update.
    }

    public void doUpdateFail() {
        // Fail update.
    }

    public void checkUpdateFail() {
        // Check update fail.
    }

}
