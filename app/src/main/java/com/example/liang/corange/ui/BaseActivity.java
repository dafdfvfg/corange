package com.example.liang.corange.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.liang.corange.common.SystemStatusManager;
import com.example.liang.corange.element.EventIds;
import com.example.liang.corange.element.Global;
import com.example.liang.corange.manager.OrangeActivityManager;
import com.example.liang.corange.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;

/**
 * BaseActivity
 * Created by liang on 1/8/2017.
 */

public class BaseActivity extends AppCompatActivity {
    protected boolean pauseClean = true;
    private List<RxBus.GlobalListener> globalListeners;
    private OperationDialog opDilaog;
    private Toast mToast;
    private RxBus.GlobalListener listener = new RxBus.GlobalListener() {
        @Override
        public void onGlobalEvent(int eventId, Object... args) {
            int mState = (int) args[0];

            if (0 == mState) {// 网络可用
                Log.i("wocao","keyong");
                if(opDilaog!=null&&opDilaog.isShowing()){
                    opDilaog.dismiss();
                }
                toast("网络已恢复");
            } else {
                Log.i("wocao","bukeyong");
                showDialog();

            }
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        Global.init(this);
        OrangeActivityManager.getInstance().addActivity(BaseActivity.this);
        registerEvent(EventIds.EVENT_NET_CHANGE, listener);
    }

    /**
     * 设置状态栏背景状态
     */
    private void setTranslucentStatus() {
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemStatusManager tintManager = new SystemStatusManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(0);//状态栏无背景
    }

    public RxBus.GlobalListener registerEvent(int eventId, RxBus.GlobalListener action1) {
        RxBus.GlobalListener listener = RxBus.get().register(eventId, action1);
        if (globalListeners == null) {
            globalListeners = new ArrayList<>();
        }
        globalListeners.add(listener);
        return listener;
    }

    public void setPauseClean(boolean pauseClean) {
        this.pauseClean = pauseClean;
    }

    public void showDialog() {
        opDilaog = new OperationDialog(BaseActivity.this);
        opDilaog.setOpTitle("提示");
        opDilaog.addTextContentView("当前网络不可用");
        opDilaog.setOk("确认");
        opDilaog.hideCancleBtn();
        opDilaog.addOnClick(new OperationDialog.OnClick() {
            @Override
            public void cancel() {

            }

            @Override
            public void ok(String content) {
                opDilaog.dismiss();
            }
        });
        opDilaog.setCanceledOnTouchOutside(false);
        opDilaog.show();
    }
    public void toast(int string) {
        toast(getString(string));
    }

    public void toast(String message) {
        if (null == message || message.trim().length() <= 0) {
            return;
        }
        if (BaseActivity.this != null) {
            if (mToast == null) {
                mToast = Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(message);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (pauseClean) {
            if (globalListeners != null) {
                for (RxBus.GlobalListener l : globalListeners) {
                    RxBus.get().unregisterAll(l);
                }
                globalListeners.clear();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OrangeActivityManager.getInstance().removeActivity(this);
        if (globalListeners != null) {
            for (RxBus.GlobalListener l : globalListeners) {
                RxBus.get().unregisterAll(l);
            }
            globalListeners.clear();
        }
    }
}
