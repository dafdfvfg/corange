package com.example.liang.corange;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;

import com.example.liang.corange.broadcast.NetStateChangeBroadCastReciver;

/**
 * Created by liang on 1/7/2017.
 */

public class BaseApplication extends Application {
    private static Context getInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        getInstance =BaseApplication.this;
        BroadcastReceiver receiver = new NetStateChangeBroadCastReciver();
        //注册BroadCastReciver,设置监听的频道。就是filter中的
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    public static Context getInstance() {
        return getInstance;
    }

    public static void exitApp() {
        ((BaseApplication) getInstance).handler.sendEmptyMessage(0);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.exit(0);
        }
    };
}
