package com.example.liang.corange.element;

import android.app.Activity;
import android.content.Intent;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * Created by liang on 2017/5/2.
 */

public class Global {

    private static AtomicBoolean hasInit = null;


    public static void init(Activity activity) {
        if (hasInit != null && hasInit.get()) {
            return;
        }
        if (hasInit == null /*&& !(activity instanceof SplashActivity)*/) {
            /* init Global parameter here!!!☝ */
            hasInit = new AtomicBoolean(true);


//            if (!(activity instanceof SplashActivity)) {
//                L.get().e("Global", "go to Splash from " + activity.getClass().getSimpleName());
//                activity.startActivity(new Intent(activity, SplashActivity.class));
//                activity.finish();
//            }

            return;
        }

        // hasInit.set(fromSplash);
        // if (activity instanceof SplashActivity) {
        //     hasInit = new AtomicBoolean(true);
        // }
    }

    public static boolean hasInit() {
        return hasInit != null && hasInit.get();
    }

    public static void setInit() {
        hasInit = new AtomicBoolean(true);
    }
}
