package com.example.liang.corange.listener;

import android.net.NetworkInfo.State;

public interface NetStateListener {
    void NetState(boolean connected, State mState, String flag);
}
