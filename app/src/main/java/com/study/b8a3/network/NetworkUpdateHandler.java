package com.study.b8a3.network;

import android.content.Context;
import android.text.TextUtils;

import com.study.b8a3.common.ErrorCode;
import com.study.b8a3.thread.UpdateHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class NetworkUpdateHandler extends UpdateHandler {

    private List<NameValuePair> mParams = null;
    private String mResponse;
    private String url;
    private int code = ErrorCode.SUCCESS;
    private String errorMessage = "";

    public NetworkUpdateHandler(Context context, String url, Map<String, String> params) {
        super(context);
        this.url = url;
        setParams(params);
    }

    @Override
    public void doUpdateBefore() {
        super.doUpdateBefore();
        setDefaultParams();
    }

    protected void setDefaultParams() {
        if (mParams == null) {
            mParams = new ArrayList<>();
        }

//        mParams.add(new NameValuePair("dit", ""));
    }

    protected void setParams(String name, String value) {
        if (mParams == null) {
            mParams = new ArrayList<>();
        }
        if (TextUtils.isEmpty(name)) return;
        mParams.add(new NameValuePair(name, value));
    }

    protected void setParams(Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            setParams(entry.getKey(), entry.getValue());
        }
    }

    protected List<NameValuePair> getParams() {
        return mParams;
    }


    @Override
    public boolean doUpdateNow() {
        mResponse = null;
        try {
            mResponse = Network.doHttpPost(mContext, url, getParams());
        } catch (IOException e) {
            e.printStackTrace();
            code = ErrorCode.ERROR_NET;
            return false;
        }
        if (TextUtils.isEmpty(mResponse)) {
            code = ErrorCode.ERROR_NO_RESPONSE;
            return false;
        }

        int analyseCode = analyseResponse(mResponse);
        switch (analyseCode) {
            case ErrorCode.ERROR_FORMAT:
                code = ErrorCode.ERROR_FORMAT;
                return false;
            case ErrorCode.SUCCESS:
                return true;
            default:
                code = analyseCode;
                return false;
        }

    }

    protected abstract int analyseResponse(String response);

    protected abstract boolean success(String response);

    protected abstract boolean fail(int code);

    @Override
    public void doUpdateSuccess() {
        success(mResponse);
    }

    @Override
    public void doUpdateFail() {
        fail(code);
    }
}
