package com.example.liang.corange.utils;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BrandCustomizationUtils {
    /**
     * setDarkStatusBar on FlyMe
     * 设置状态栏字体为暗色 仅魅族有效
     * 黑底白字 -> 白底黑字
     */
    public static void setFlyMeStatusBarColor(Activity activity, boolean isDark) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        try {
            Class<?> instance = Class.forName("android.view.WindowManager$LayoutParams");
            int value = instance.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(lp);
            Field field = instance.getDeclaredField("meizuFlags");
            field.setAccessible(true);
            int origin = field.getInt(lp);
            if (isDark) {
                field.set(lp, origin | value);
            } else {
                field.set(lp, (~value) & origin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setMIUIStatusBarColor(Activity context, boolean isDark) {
//        if (!isMiUIV6()){
//            DebugLog.d("isMiUIV6:"+false);
//            return;
//        }
//        DebugLog.d("isMiUIV6:"+true);
        Window window = context.getWindow();
        Class clazz = window.getClass();
        try {
            int tranceFlag = 0;
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
            tranceFlag = field.getInt(layoutParams);
            field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
//            if (type == 0){
//                extraFlagField.invoke(window, tranceFlag, tranceFlag);//只需要状态栏透明
//            }else if(type == 1){
//                extraFlagField.invoke(window, tranceFlag | darkModeFlag, tranceFlag | darkModeFlag);//状态栏透明且黑色字体
//            }else {
//                extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
//            }
            if (isDark) {
                extraFlagField.invoke(window, tranceFlag | darkModeFlag, tranceFlag | darkModeFlag);
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
