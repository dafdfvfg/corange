package com.example.liang.corange.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.example.liang.corange.BaseApplication;
import com.example.liang.corange.element.EventIds;
import com.example.liang.corange.element.State;
import com.example.liang.corange.listener.NetStateListener;
import com.example.liang.corange.utils.NetWorkUtils;
import com.example.liang.corange.utils.RxBus;


public class NetStateChangeBroadCastReciver extends BroadcastReceiver {

    private NetStateListener netStateListener = null;

    public NetStateChangeBroadCastReciver() {
    }

    public NetStateChangeBroadCastReciver(NetStateListener netStateListener) {
        this.netStateListener = netStateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //判断wifi是打开还是关闭
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) { //此处无实际作用，只是看开关是否开启
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    break;

                case WifiManager.WIFI_STATE_DISABLING:
                    break;
            }
        }
        //如果是在开启wifi连接和有网络状态下
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            int newState;
            if (NetWorkUtils.isConnected(context)) {
                newState = 0;
            } else {
                newState = 1;
            }
            if (State.netState != newState) {
                State.netState = newState;
                RxBus.get().post(EventIds.EVENT_NET_CHANGE, State.netState);
            }

        }
    }

}
