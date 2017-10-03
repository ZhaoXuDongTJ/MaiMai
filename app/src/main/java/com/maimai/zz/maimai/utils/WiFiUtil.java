package com.maimai.zz.maimai.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 92198 on 2017/10/3.
 */

public class WiFiUtil {
    /**
     * 判断是否连接WIFI
     * @param context  上下文
     * @return  boolean
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
