package com.example.liang.corange.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.liang.corange.R;


public class StatusBarCompat {
    private static final int INVALID_VAL = -1;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, int statusColor) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (statusColor != INVALID_VAL) {
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        //| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                );
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                window.setStatusBarColor(statusColor);

            }
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            int color = ContextCompat.getColor(activity, R.color.color_trans_white);
//            if(COLOR_DEFAULT!=statusColor) {
//                if (tintManager == null)
//                    tintManager = new SystemBarTintManager(activity);
//                // 启用状态栏渐变
//                tintManager.setStatusBarTintEnabled(true);
//                //设置状态栏颜色与ActionBar颜色相连
//                tintManager.setStatusBarTintColor(0xffff0000);
//            }
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            if (statusColor != INVALID_VAL) {
                color = statusColor;
            }

            View statusBarView = contentView.getChildAt(0);
            //改变颜色时避免重复添加statusBarView
            if (statusBarView != null && statusBarView.getMeasuredHeight() == getStatusBarHeight(activity)) {
                statusBarView.setBackgroundColor(color);
                return;
            }
            statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(color);
            contentView.addView(statusBarView, lp);
        }

    }

    public static void compat(Activity activity) {
        compat(activity, INVALID_VAL);
    }


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
//		if(Constant.barHeight>0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//			Window window = getWindow();
//			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//					//| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
//			);
//			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//					//| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//			window.setStatusBarColor(0xffff0000);
////			window.setNavigationBarColor(Color.TRANSPARENT);
//		}
//		else
//		if (Constant.barHeight>0 && Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT) {
//			Window window = getWindow();
//			// Translucent status bar
//			window.setFlags(
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//		}
//		setDark(false);