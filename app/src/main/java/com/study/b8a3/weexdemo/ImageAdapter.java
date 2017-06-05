package com.study.b8a3.weexdemo;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.study.b8a3.main.B8a3Application;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

/**
 * Created by Administrator on 2017/6/5.
 */

public class ImageAdapter implements IWXImgLoaderAdapter {
    public static final String TAG = ImageAdapter.class.getSimpleName();

    @Override
    public void setImage(String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
        Glide.with(B8a3Application.getContext())
                .load(url)
                .into(view);
        Log.d(TAG, url);
    }
}
