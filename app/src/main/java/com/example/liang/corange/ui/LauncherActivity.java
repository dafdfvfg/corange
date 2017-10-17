package com.example.liang.corange.ui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.example.liang.corange.R;
import com.example.liang.corange.utils.SharedConfig;

/**
 * Created by liang on 2017/5/17.
 */
public class LauncherActivity extends BaseActivity {
    private boolean first;
    private View view;
    private Context context;
    private Animation animation;
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_main_welcome, null);
        overridePendingTransition(0,0);
        setContentView(view);
        context = this;
        shared = new SharedConfig(context).GetConfig();
    }

    @Override
    protected void onResume() {
        into();
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    private void into() {
        first = shared.getBoolean("First", true);
        animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        view.startAnimation(animation);
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                Intent intent;
                if (first) {
                    intent = new Intent(LauncherActivity.this, WelcomeActivity.class);
                } else {
                    intent = new Intent(LauncherActivity.this, MainActivity.class);
                }
                startActivity(intent);
                LauncherActivity.this.finish();
            }

        });
    }
}
