package com.study.b8a3.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.study.b8a3.main.B8a3Application;


public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final int SIGNAL_LEVELS = 5;
    private static final int MAX_PERCENT = 100;
    /**
     * 网络连接类型
     * In general, do not share the
     * service objects between various different contexts (Activities, Applications,
     * Services, Providers, etc.)
     */
    public static boolean isWifiConnect() {
        ConnectivityManager cm = (ConnectivityManager) B8a3Application.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (null != info) {
            switch (info.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                case ConnectivityManager.TYPE_ETHERNET:
                case ConnectivityManager.TYPE_BLUETOOTH:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    /**
     * 获取网络状态 -1 没有网络, 0 mobie, 1 wifi
     * In general, do not share the
     * service objects between various different contexts (Activities, Applications,
     * Services, Providers, etc.)
     */
    public static int getNetworkType() {
        ConnectivityManager cm = (ConnectivityManager) B8a3Application.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            return networkInfo.getType();
        }
        return -1;
    }

    /**
     * 网络是否可用
     * In general, do not share the
     * service objects between various different contexts (Activities, Applications,
     * Services, Providers, etc.)
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) B8a3Application.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public static boolean isProxyEnabled(Context context) {
        return android.provider.Settings.Secure.getInt(
                context.getContentResolver(), "http_proxy_enabled", 1) != 0;
    }

    public static boolean isProxyForWifiOnly(Context context) {
        return android.provider.Settings.Secure.getInt(
                context.getContentResolver(), "http_proxy_wifi_only", 0) != 0;
    }

    public static int getWifiStrengthPercent() {
        WifiManager wifiManager = (WifiManager) B8a3Application.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getBSSID() == null) {
            return -1;
        }
        int strength = wifiManager.calculateSignalLevel(wifiInfo.getRssi(), SIGNAL_LEVELS);
        return  strength * MAX_PERCENT / (SIGNAL_LEVELS - 1);
    }
}
