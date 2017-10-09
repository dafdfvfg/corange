package com.example.liang.corange.utils;

import android.content.Context;
import com.example.liang.corange.common.Configure;
import java.lang.reflect.Field;

/**
 *
 * Created by liang on 2017/4/20.
 */

public class getStatusBarHeight {
    public int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Configure.barHeight = sbar;
        return sbar;
    }
}
