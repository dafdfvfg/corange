package com.example.liang.corange.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by liang on 1/8/2017.
 */

public class HelloBroadcastReceiver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"我是第二个",Toast.LENGTH_SHORT).show();
    }
}
