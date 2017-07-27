package com.study.b8a3.thread;

import android.app.IntentService;
import android.content.Intent;
import android.nfc.Tag;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by B8A3 on 2017/7/27.
 */

public class LocalIntentService extends IntentService {
    public static String TAG = LocalIntentService.class.getSimpleName();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public LocalIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getStringExtra("task_action");
        Log.d(TAG , "receive task: "+action);
        SystemClock.sleep(1000);
        if("test".equals(action)){
            Log.e(TAG, "handle task: "+action);
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "service is destroy");
        super.onDestroy();

    }
}
