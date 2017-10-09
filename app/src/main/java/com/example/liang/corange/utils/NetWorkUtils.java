package com.example.liang.corange.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;


/**
 * 网络 工具类<Br>
 * 内部已经封装了打印功能,只需要把DEBUG参数改为true即可<br>
 * 如果需要更换tag可以直接更改,默认为KEZHUANG
 *
 * @author ZC
 */
public class NetWorkUtils {
    // /**
    // * Log的开关<br>
    // * true为开启<br>
    // * false为关闭<br>
    // */
    // public static boolean DEBUG = InnerContacts.DEFAULT_DEBUG;

    /**
     * Log 输出标签
     */
    public final static String TAG = "NetWorkUtils";

    /**
     * 接受网络状态的广播Action
     */
    public static final String NET_BROADCAST_ACTION = "com.network.state.action";

    public static final String NET_STATE_NAME = "network_state";
    // 网络监听变化服务action
    public static final String net_change_service_action = "com.mll.sdk.service.ner.change";
    /**
     * 实时更新网络状态<br>
     * -1为网络无连接<br>
     * 1为WIFI<br>
     * 2为移动网络<br>
     */
    public static int CURRENT_NETWORK_STATE = -1;

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivity.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivity.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }

        }

        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null || cm.getActiveNetworkInfo() == null) {
            // LogUtil.d(context, TAG, context.getString(com.mll.sdk.R.string.avalible_not_net), true);
            return false;
        }
        boolean isWifi = cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
        // if (isWifi)
        // 	LogUtil.i(context, TAG, context.getString(com.mll.sdk.R.string.wifi_net), true);
        // else
        // 	LogUtil.i(context, TAG, context.getString(com.mll.sdk.R.string.not_wifi_net), true);
        return isWifi;
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    public static int getCurrentNetType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != cm) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null) {
                return info.getType();
            }
        }

        return -1;
    }

}
