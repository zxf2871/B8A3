package com.study.b8a3.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;

/**
 * Created by B8A3 on 2017/4/13.
 */

public class RecyclerViewActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view_layout);
        mRecyclerView = (RecyclerView)findViewById(R.id.rcv);
    }

    public static void openActivity(Context context){
        context.startActivity(new Intent(context, RecyclerViewActivity.class));
    }
}
