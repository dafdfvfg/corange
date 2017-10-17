package com.example.liang.corange.ui;


import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liang.corange.R;
import com.example.liang.corange.utils.RxBus;
import com.example.liang.corange.views.TitleView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**


 */
public class BaseFragment extends Fragment implements View.OnClickListener {

    protected Toast mToast;

    protected Dialog dialog;

    // private CompositeSubscription compositeSubscription = new CompositeSubscription();
    protected boolean pauseClean = true;
    private List<RxBus.GlobalListener> globalListeners;

    public BaseFragment() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        // compositeSubscription.clear();
        if (globalListeners != null) {
            for (RxBus.GlobalListener l : globalListeners) {
                RxBus.get().unregisterAll(l);
            }
            globalListeners.clear();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideInput();
    }

    @Override
    public void onResume() {
        super.onResume();

//        StatService.trackCustomBeginEvent(getActivity(), "fragment2", getClass().getSimpleName());
//        StatService.trackBeginPage(getActivity(), getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();

//        StatService.trackCustomEndEvent(getActivity(), "fragment2", getClass().getSimpleName());
//        StatService.trackEndPage(getActivity(), getClass().getSimpleName());
        if (pauseClean) {
            if (globalListeners != null) {
                for (RxBus.GlobalListener l : globalListeners) {
                    RxBus.get().unregisterAll(l);
                }
                globalListeners.clear();
            }
        }
    }

    public void setViewPadding(View... views) {
        TitleView.setViewPadding(getActivity(), views);
    }

    public void showProgressDialog() {
        showProgressDialog(true);
    }

    public void showProgressDialog(String text) {
//        showProgressDialog(false, R.layout.dialog_process, 0, text);
    }

    protected void showProgressDialog(int layoutID) {
        showProgressDialog(true, layoutID);
    }

    public void showProgressDialog(boolean isCancel, int layoutID) {
//        showProgressDialog(isCancel, layoutID, 0, "");
    }

    public void showProgressDialog(int image, int text) {
//        showProgressDialog(false, R.layout.dialog_process, image, getString(text));
    }

    public void showProgressDialog(boolean isCancel) {
        showProgressDialog(isCancel, R.layout.dialog_process);
    }

//    public void showProgressDialog(boolean isCancel, int layoutID, int image, String text) {
//        try {
//            // if (handler != null) handler.removeCallbacks(dismissDialogRunnable);
//            // ProgressDialog oldDialog = dialog;
//
//            if (dialog == null || !dialog.isShowing()) {
//                dialog = new ProgressDialog(getContext());
//                dialog.show();
//            }
//
//            View loading_view;
//            loading_view = getLayoutInflater(null).inflate(layoutID,null, false);
//            dialog.setContentView(loading_view);
//
//
//            ImageView ivLoading = (ImageView) loading_view.findViewById(R.id.iv_loading);
//            TextView tvLoading = (TextView) loading_view.findViewById(R.id.tv_loading);
//
//            if (ivLoading != null && image > 0) {
//                ivLoading.setImageResource(image);
//            }
//
//            if (tvLoading != null) {
//                tvLoading.setText(text);
//            }
//
//            dialog.setCancelable(isCancel);
//        } catch (Exception e) {
//
//        }
//    }


    public void dismissDialog() {
        if (dialog != null) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                // e.printStackTrace();
            }
            dialog = null;
        }
    }

    public boolean onBackPressed(Activity activity) {
        finish();
        return true;
    }

    public void toast(int string) {
        toast(getString(string));
    }

    public void toast(String message) {
        if (null == message || message.trim().length() <= 0) {
            return;
        }
        if (getActivity() != null) {
            if (mToast == null) {
                mToast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(message);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
        }
    }

//    public DialogManager showTipDialog(int message) {
//        DialogManager dlg = new DialogManager(getContext(), DialogManager.SIMPLE_TEXT_DIALOG);
//        dlg.setTitle(R.string.tip);
//        dlg.setPositiveButton(R.string.i_see, null);
//        dlg.setMessage(message);
//
//        dlg.show(getFragmentManager());
//        return dlg;
//    }

    public void finish() {
        BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
        if (activity == null) {
            return;
        }

        if (activity.getSupportFragmentManager().getBackStackEntryCount() > 1) {
            (activity).popupFragment();
        } else {
            (activity).finish();
        }
    }


    //隐藏软键盘
    public void hideInput() {
        try {
            /* if (isDetached() || !isVisible()) {
                return;
            } */

            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showInput(final EditText et) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    InputMethodManager inManager = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    // inManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    inManager.showSoftInput(et, 0);
                    inManager.showSoftInput(et, InputMethodManager.SHOW_FORCED);
                    et.requestFocus();
                    et.performClick();
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        }, 300);

    }

    @Override
    public void onClick(View v) {

    }

    public void onFragmentResult(int requestCode, int resultCode, Intent data) {

    }

    public void setResult(int result) {
        setResult(result, null);
    }

    public void setResult(int result, Intent data) {
        BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
        if (activity == null) {
            return;
        }

        activity.setFragmentResult(this, result, data);
    }

    public RxBus.GlobalListener registerEvent(int eventId, RxBus.GlobalListener action1) {
        RxBus.GlobalListener listener = RxBus.get().register(eventId, action1);
        if (globalListeners == null) {
            globalListeners = new ArrayList<>();
        }
        globalListeners.add(listener);
        //        if (observables == null) {
        //            observables = new HashMap<>();
        //        }
        //        observables.put(tag, observable);
        //        return observable;
        return listener;
    }

    public void setPauseClean(boolean pauseClean) {
        this.pauseClean = pauseClean;
    }

    public void showFragment(String fname, Bundle args) {
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) activity).showFragment(fname, args);
        }
    }

    public void showFragment(String fname, Bundle args, Integer reqCode) {
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) activity).showFragment(fname, args, reqCode);
        }
    }

    public void popupFragment() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) activity).popupFragment();
        }
    }

    public void pressBack() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }
}
