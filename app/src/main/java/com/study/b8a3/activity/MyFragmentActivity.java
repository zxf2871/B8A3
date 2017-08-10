package com.study.b8a3.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.study.b8a3.R;
import com.study.b8a3.main.BaseActivity;

public class MyFragmentActivity extends BaseActivity {


    MyFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getSupportFragmentManager();
        mFragment = (MyFragment) fm.findFragmentById(R.id.fragment_container);

        if (mFragment == null) {
            mFragment = new MyFragment();
            fm.beginTransaction().add(R.id.fragment_container, mFragment).commit();
        }


    }
}
