// Copyright (c) 2016 The HAWK Inc. All rights reserved.
// Author: HAWK Browser Team.

package com.study.b8a3.thread;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class ThreadServices implements Runnable {
    private static final String LOGTAG = ThreadServices.class.getSimpleName();

    private List<UpdateHandler> mRunningList = new CopyOnWriteArrayList<>();
    private List<UpdateHandler> mWaitingList = new CopyOnWriteArrayList<>();

    private final ScheduledExecutorService THREAD_POOL_EXECUTOR = Executors.newScheduledThreadPool(20);

    private static ThreadServices sInstance = null;

    private ThreadServices() {
    }

    public ScheduledExecutorService getThreadPool() {
        return THREAD_POOL_EXECUTOR;
    }

    public static ThreadServices getInstance() {
        if (sInstance == null) {
            sInstance = new ThreadServices();
        }

        return sInstance;
    }

    public void updateServices(UpdateHandler handler) {
        if (null == handler) return;
        if (!mWaitingList.contains(handler)) {
            mWaitingList.add(handler);
        }
        THREAD_POOL_EXECUTOR.execute(this);
    }

    @Override
    public void run() {
        synchronized (this) {
            for (UpdateHandler handler : mWaitingList) {
                if (!mRunningList.contains(handler)) {
                    mRunningList.add(handler);
                }
                mWaitingList.remove(handler);
            }
            for (UpdateHandler handler : mRunningList) {
                // Handler will be scheduled with a requested delay in milliseconds.
                THREAD_POOL_EXECUTOR.schedule(
                        new UpdaterRunnable(handler),
                        handler.delay(),
                        TimeUnit.MILLISECONDS);

                mRunningList.remove(handler);
            }
        }
    }

    private class UpdaterRunnable implements Runnable {
        private UpdateHandler mHandler;

        public UpdaterRunnable(UpdateHandler handler) {
            mHandler = handler;
        }

        @Override
        public void run() {
            mHandler.initForUpdate();
            if (mHandler.checkUpdate()) {
                try {
                    mHandler.doUpdateBefore();
                    if (mHandler.doUpdateNow()) {
                        mHandler.doUpdateSuccess();
                    } else {
                        mHandler.doUpdateFail();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.doUpdateFail();
                }
            } else {
                mHandler.checkUpdateFail();
            }
        }
    }

}
