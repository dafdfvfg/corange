package com.example.liang.corange.manager;

import android.app.Activity;
import android.content.Context;

import com.example.liang.corange.BaseApplication;

import java.util.List;
import java.util.Stack;

/**
 * activity管理类
 * Created by liang on 2017/5/2.
 */

public class OrangeActivityManager {
    public static Stack<Activity> activityStack;
    public static Stack<Activity> shortActivityStack;
    public static OrangeActivityManager instance;

    public OrangeActivityManager() {

    }

    /**
     * 单实例 , UI无需考虑多线程同步问题
     */
    public static OrangeActivityManager getInstance() {
        if (instance == null) {
            instance = new OrangeActivityManager();
        }
        return instance;
    }

    /**
     * 添加Activity到栈
     */
    public static void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        if (activity != null) {
            activityStack.add(activity);
        }
    }

    public static void removeActivity(Activity activity) {
        if (activityStack != null && activity != null) {
            activityStack.remove(activity);
        }
    }
    public static void addShortActivity(Activity activity) {
        if (shortActivityStack == null) {
            shortActivityStack = new Stack<Activity>();
        }
        if (activity != null) {
            shortActivityStack.add(activity);
        }
    }
    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果class不存在于栈中，则栈全部清空
     */
    public void popOthersActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                for (int i = activityStack.size() - 1; i >= 0; i--) {
                    if (activityStack.get(i) != activity) {
                        activityStack.get(i).finish();
                        activityStack.remove(activityStack.get(i));
                    }
                }
            }
        }
    }
    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
        finishAllShortActivity();
    }
    public void finishAllShortActivity() {
        if (shortActivityStack == null) return;

        for (int i = 0, size = shortActivityStack.size(); i < size; i++) {
            if (null != shortActivityStack.get(i)) {
                shortActivityStack.get(i).finish();
                finishActivity(shortActivityStack.get(i));
            }
        }
        shortActivityStack.clear();
    }
    /**
     * 应用程序退出
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            //activityMgr.killBackgroundProcesses(context.getPackageName());
            //System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
    }
    /**
     * 返回当前的应用是否处于前台显示状态
     *
     * @return
     */
    public boolean isTopActivity() {
        //_context是一个保存的上下文
        try {
            android.app.ActivityManager am = (android.app.ActivityManager) BaseApplication.getInstance().getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<android.app.ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
            if (list.size() == 0)
                return false;
            android.app.ActivityManager.RunningAppProcessInfo process = list.get(0);
            if (process.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                    process.processName.equals(BaseApplication.getInstance().getPackageName())) {
                return true;
            }

        } catch (Exception e) {
        }
        return false;
    }
}
