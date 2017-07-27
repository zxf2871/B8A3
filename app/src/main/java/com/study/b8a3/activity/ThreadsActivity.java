package com.study.b8a3.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;
import com.study.b8a3.thread.LocalIntentService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ThreadsActivity extends BaseActivity {

    public static final String TAG = ThreadsActivity.class.getSimpleName();
    private Button btn_asynctask;
    private List<AsyncTask> list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        btn_asynctask = (Button) findViewById(R.id.btn_asynctask);
    }


    public void btnAsyncTask(View view) {
        MyAsyncTask t1 =   new MyAsyncTask("AsyncTask#1", btn_asynctask);
        MyAsyncTask t2 =   new MyAsyncTask("AsyncTask#2", btn_asynctask);
        MyAsyncTask t3 =   new MyAsyncTask("AsyncTask#3", btn_asynctask);
        MyAsyncTask t4 =   new MyAsyncTask("AsyncTask#4", btn_asynctask);
        MyAsyncTask t5 =   new MyAsyncTask("AsyncTask#5", btn_asynctask);
        MyAsyncTask t6 =   new MyAsyncTask("AsyncTask#6", btn_asynctask);

        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);
        list.add(t5);
        list.add(t6);

//        t1.execute("");
//        t2.execute("");
//        t3.execute("");
//        t4.execute("");
//        t5.execute("");
//        t6.execute("");

        //并行处理
        t1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        t2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        t3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        t4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        t5.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        t6.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");

    }

    public void btnIntentService(View view) {
        Intent intent = new Intent(this, LocalIntentService.class);
        intent.putExtra("task_action", "test");
        startService(intent);

        intent.putExtra("task_action", "test1");
        startService(intent);

        intent.putExtra("task_action", "test2");
        startService(intent);

        intent.putExtra("task_action", "test3");
        startService(intent);

    }

    private static class MyAsyncTask extends AsyncTask<String, String, String> {

        private String mName = "AsyncTask";
        private Button mButton;

        public MyAsyncTask(String name, Button button) {
            super();
            mName = name;
            mButton = button;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(1500);
                String result = "";
                for (int i = 0; i < 20; i++) {
                    Thread.sleep(100L);
                    result = result+"|";
                    publishProgress(result);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return mName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            mButton.setText(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            Log.e(TAG, s + "execute finish at " + df.format(new Date()));
            mButton.setText(s);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.e(TAG, mName+" is on cancelled");

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(AsyncTask st:list){
            st.cancel(true);
        }
    }
}
