package com.example.liang.corange.ui;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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
 * BaseFragment
 * Created by liang on 1/8/2017.
 */
public class BaseFragment extends Fragment implements View.OnClickListener {
    protected Toast mToast;
    protected Dialog dialog;

    public BaseFragment() {
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

    public void setViewPadding(View... views) {
        TitleView.setViewPadding(getActivity(), views);
    }

    public void showProgressDialog() {
        showProgressDialog(true);
    }

    public void showProgressDialog(String text) {
        showProgressDialog(false, R.layout.dialog_process, 0, text);
    }

    protected void showProgressDialog(int layoutID) {
        showProgressDialog(true, layoutID);
    }

    public void showProgressDialog(boolean isCancel, int layoutID) {
        showProgressDialog(isCancel, layoutID, 0, "");
    }

    public void showProgressDialog(int image, int text) {
        showProgressDialog(false, R.layout.dialog_process, image, getString(text));
    }

    public void showProgressDialog(boolean isCancel) {
        showProgressDialog(isCancel, R.layout.dialog_process);
    }

    public void showProgressDialog(boolean isCancel, int layoutID, int image, String text) {
        try {
            if (dialog == null || !dialog.isShowing()) {
                dialog = new ProgressDialog(getContext());
                dialog.show();
            }

            View loading_view;
            loading_view = getLayoutInflater(null).inflate(layoutID,
                    null, false);
            dialog.setContentView(loading_view);

            ImageView ivLoading = (ImageView) loading_view.findViewById(R.id.iv_loading);
            TextView tvLoading = (TextView) loading_view.findViewById(R.id.tv_loading);

            if (ivLoading != null && image > 0) {
                ivLoading.setImageResource(image);
            }

            if (tvLoading != null) {
                tvLoading.setText(text);
            }

            dialog.setCancelable(isCancel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void dismissDialog() {
        if (dialog != null) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideInput();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideInput();
    }
}
