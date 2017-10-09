package com.example.liang.corange.business.homepage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.example.liang.corange.R;
import com.example.liang.corange.dialog.ColorDialog;
import com.example.liang.corange.dialog.PromptDialog;
import com.example.liang.corange.ui.BaseFragment;
import com.example.liang.corange.ui.BaseFragmentActivity;
import com.example.liang.corange.ui.TestActivity;
import com.example.liang.corange.utils.AnimationUtils;
import com.example.liang.corange.views.TitleView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 首页
 * Created by liang on 1/8/2017.
 */

public class HomeFragment extends BaseFragment {
    private TitleView titleView;
    private Button mButton;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_main, null);
        initWidget(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initWidget(View view) {
        titleView = (TitleView) view.findViewById(R.id.title_view);
        titleView.setLeftDrawableVisible(false);
        mButton = (Button) view.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("哎呀我去！");
//                BaseFragmentActivity.startFragment(getActivity(), TestFragment.class, null);
//                Intent intent = new Intent(getActivity(),TestActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

            }
        });

    }
}
