package com.example.liang.corange.ui;


import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liang.corange.R;
import com.example.liang.corange.business.homepage.DiscoverFragment;
import com.example.liang.corange.business.homepage.HomeFragment;
import com.example.liang.corange.business.homepage.MineFragment;
import com.example.liang.corange.business.homepage.NearbyFragment;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private long exitTime = 0;
    private int total = 4;
    private View page1, page2, page3, page4;
    private ImageView[] ivs;
    private TextView[] tvs;
    private Drawable[] defaultDrawables, selectedDrawables;
    private HomeFragment homeFragment;
    private NearbyFragment nearbyFragment;
    private DiscoverFragment discoverFragment;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0,0);
        initWidget();
        PgyUpdateManager.register(MainActivity.this, null,
                new UpdateManagerListener() {

                    @Override
                    public void onUpdateAvailable(final String result) {

                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("更新")
                                .setMessage(appBean.getReleaseNote())
                                .setNegativeButton(
                                        "确定",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                startDownloadTask(
                                                        MainActivity.this,
                                                        appBean.getDownloadURL());
                                            }
                                        }).show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                    }
                });
    }

    private void initWidget() {
        page1 = findViewById(R.id.page_1);
        page2 = findViewById(R.id.page_2);
        page3 = findViewById(R.id.page_3);
        page4 = findViewById(R.id.page_4);

        page1.setOnClickListener(this);
        page2.setOnClickListener(this);
        page3.setOnClickListener(this);
        page4.setOnClickListener(this);

        ivs = new ImageView[]{(ImageView) findViewById(R.id.home_iv),
                (ImageView) findViewById(R.id.nearby_iv),
                (ImageView) findViewById(R.id.discover_iv),
                (ImageView) findViewById(R.id.mine_iv)};
        tvs = new TextView[]{(TextView) findViewById(R.id.home_tv),
                (TextView) findViewById(R.id.nearby_tv),
                (TextView) findViewById(R.id.discover_tv),
                (TextView) findViewById(R.id.mine_tv)};
        defaultDrawables = new Drawable[]{ContextCompat.getDrawable(this, R.drawable.tab_ic_home_normal),
                ContextCompat.getDrawable(this, R.drawable.tab_ic_nearby_normal),
                ContextCompat.getDrawable(this, R.drawable.tab_ic_featured_normal),
                ContextCompat.getDrawable(this, R.drawable.tab_ic_mine_normal)};

        selectedDrawables = new Drawable[]{ContextCompat.getDrawable(this, R.drawable.tab_ic_home_highlight),
                ContextCompat.getDrawable(this, R.drawable.tab_ic_nearby_highlight),
                ContextCompat.getDrawable(this, R.drawable.tab_ic_featured_highlight),
                ContextCompat.getDrawable(this, R.drawable.tab_ic_mine_highlight)};

        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        if (nearbyFragment == null) {
            nearbyFragment = new NearbyFragment();
        }
        if (discoverFragment == null) {
            discoverFragment = new DiscoverFragment();
        }
        if (mineFragment == null) {
            mineFragment = new MineFragment();
        }

        ResetTab(0);
    }

    private void ResetTab(int i) {
        for (int j = 0; j < total; j++) {
            tvs[j].setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color_9));
            ivs[j].setImageDrawable(defaultDrawables[j]);
        }
        tvs[i].setTextColor(0xffff6666);
        ivs[i].setImageDrawable(selectedDrawables[i]);
        switch (i) {
            case 0:
                getFragmentManager().beginTransaction().replace(R.id.main, homeFragment).commit();
                break;
            case 1:
                getFragmentManager().beginTransaction().replace(R.id.main, nearbyFragment).commit();
                break;
            case 2:
                getFragmentManager().beginTransaction().replace(R.id.main, discoverFragment).commit();
                break;
            case 3:
                getFragmentManager().beginTransaction().replace(R.id.main, mineFragment).commit();
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_1:
                ResetTab(0);
                break;
            case R.id.page_2:
                ResetTab(1);
                break;
            case R.id.page_3:
                ResetTab(2);
                break;
            case R.id.page_4:
                ResetTab(3);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), R.string.exit_app, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
