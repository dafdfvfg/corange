package com.example.liang.corange.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.inputmethod.InputMethodManager;

import com.example.liang.corange.R;
import com.example.liang.corange.common.Configure;
import com.example.liang.corange.element.Global;
import com.example.liang.corange.utils.BrandCustomizationUtils;
import com.example.liang.corange.utils.StatusBarCompat;


/**

 */
public class BaseFragmentActivity extends BaseActivity {

    protected int minFragments = 0;

    private static final String EXTRA_KEY_REQUEST_CDOE = "request_code_";

    private int fragmentRequestCode;
    private Integer fragmentResultCode;
    private Intent fragmentResultData;

    public static Intent getIntent(Context activity, Class<? extends Fragment> f, Bundle args) {
        Intent intent = new Intent(activity, BaseFragmentActivity.class);
        intent.putExtra("fname", f.getName());
        intent.putExtra("args", args);
        return intent;
    }

    public static void go(Context activity, Class<? extends Fragment> f, Bundle args) {
        go(activity, f.getName(), args);
    }

    public static void go(Context activity, String fname, Bundle args) {
        Intent intent = new Intent(activity, BaseFragmentActivity.class);

        intent.putExtra("fname", fname);
        intent.putExtra("args", args);
        activity.startActivity(intent);

    }

    public static void go4Result(Activity activity, int requestCode, Class<? extends Fragment> f, Bundle args) {
        Intent intent = new Intent(activity, BaseFragmentActivity.class);

        intent.putExtra("fname", f.getName());
        intent.putExtra("args", args);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Global.init(this);
        setContentView(R.layout.activity_empty_container);
        onNewIntent(getIntent());
        changeStatusBarCompat(false, 0);
//        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        if (intent != null) {
            if (intent.hasExtra("fname")) {
                showFragment(intent.getStringExtra("fname"), intent.getBundleExtra("args"));
            } else if (intent.getBooleanExtra("finish", false)) {
                intent.removeExtra("finish");
                finish();
            }
        }
    }


    private int getBrand() {
        String MODEL = Build.MODEL;
        if (MODEL == null || MODEL.length() == 0)
            return 0;
        if (MODEL.toLowerCase().contains("mi")) {
            return 1;
        } else if (MODEL.toLowerCase().contains("meizu") || MODEL.toLowerCase().contains("mx")) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * 大于等于M的没有考虑
     *
     * @param isDark
     */
    protected void changeStatusBarCompat(boolean isDark, int bgColor) {
        if (Configure.barHeight > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT /*&& Build.VERSION.SDK_INT <Build.VERSION_CODES.M*/) {
            switch (getBrand()) {
                case 1:
                    BrandCustomizationUtils.setMIUIStatusBarColor(this, isDark);
                    StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.color_trans_white));
                    break;
                case 2:
                    BrandCustomizationUtils.setFlyMeStatusBarColor(this, isDark);
                    StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.color_trans_white));
                    break;
                default:
                    if (bgColor != -1)
                        StatusBarCompat.compat(this, bgColor);
                    else
                        StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.color_trans_black));
                    break;
            }
        }
    }

    public boolean isDestroyed() {
        if (isFinishing()) {
            return true;
        }
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
                && super.isDestroyed();
    }

    public void showFragment(Class<? extends Fragment> f, Bundle args) {
        showFragment(f, args, null);
    }

    public void showFragment(Class<? extends Fragment> f, Bundle args, Integer reqCode) {
        showFragment(f.getName(), args, reqCode);
    }

    public void showFragment(String fname, Bundle args) {
        showFragment(Fragment.instantiate(this, fname, args), false, null);
    }

    public void showFragment(String fname, Bundle args, Integer reqCode) {
        showFragment(Fragment.instantiate(this, fname, args), false, reqCode);
    }

    protected void showFragment(Fragment fragment, boolean replaceMode, Integer reqCode) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentManager.getBackStackEntryCount() > minFragments) {
            //            fragmentTransaction.setCustomAnimations(
            //                    R.anim.push_in_right,
            //                    R.anim.push_out_left,
            //                    0,
            //                    R.anim.push_out_right
            //            );
        }

        if (reqCode != null) {
            Bundle args = fragment.getArguments();
            if (args == null) {
                args = new Bundle();
                fragment.setArguments(args);
            }
            args.putInt(EXTRA_KEY_REQUEST_CDOE, reqCode);
        }
        Fragment oldFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (oldFragment != null) {
            fragmentTransaction.hide(oldFragment);
        }
        String tag = fragment.getClass().getName();
        fragmentTransaction.add(R.id.fragment_container, fragment, tag);


        if (!replaceMode) {
            fragmentTransaction.addToBackStack(tag);
        }

        fragmentTransaction.commitAllowingStateLoss();
    }

    public boolean popTo(Class<? extends Fragment> fragment) {
        return popTo(fragment.getName());
    }

    public boolean popTo(String tag) {
        try {
            return getSupportFragmentManager().popBackStackImmediate(tag, 0);
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
    }

    public void popupFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > minFragments) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //            fragmentTransaction.setCustomAnimations(
            //                    R.anim.push_in_right,
            //                    R.anim.push_out_left,
            //                    0,
            //                    R.anim.push_out_right
            //            );
            fragmentManager.popBackStackImmediate();
            fragmentTransaction.commit();

            if (fragmentResultCode != null) {
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
                if (currentFragment instanceof BaseFragment) {
                    ((BaseFragment) currentFragment).onFragmentResult(fragmentRequestCode, fragmentResultCode, fragmentResultData);
                }
            }
        } else {
            if (fragmentResultCode != null) {
                setResult(fragmentResultCode, fragmentResultData);
            }
            finish();
        }

        fragmentResultCode = null;
        fragmentResultData = null;
    }

    public void finishIfNoFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment == null) {
            finish();
        }

    }
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof BaseFragment) {
            BaseFragment baseFragment = (BaseFragment) currentFragment;
            if (baseFragment.onBackPressed(this)) {
                return;
            }
        }
        if (fragmentManager.getBackStackEntryCount() > 1) {
            popupFragment();
        } else {
            finish();
        }

    }

    public void setFragmentResult(BaseFragment f, int result, Intent data) {
        fragmentRequestCode = -1;
        if (f.getArguments() != null) {
            fragmentRequestCode = f.getArguments().getInt(EXTRA_KEY_REQUEST_CDOE);
        }

        fragmentResultCode = result;
        fragmentResultData = data;
    }

    //隐藏软键盘
    public void hideInput() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (BaseFragmentActivity.this.getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(BaseFragmentActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
