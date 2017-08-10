package com.study.b8a3.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ActivityChooserView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.b8a3.R;

import java.util.zip.Inflater;

/**
 * Created by B8A3 on 2017/8/2.
 */

public class MyFragment extends Fragment {

    public static String TAG = MyFragment.class.getSimpleName();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "---->onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "---->onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "---->onCreateView");

        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_content, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "---->onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "---->onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "---->onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "---->onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "---->onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "---->onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "---->onDetach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "---->onActivityCreated");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "---->onSaveInstanceState");

    }
}
