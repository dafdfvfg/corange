package com.example.liang.corange.business.homepage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.liang.corange.adapter.HomeFragmentListAdapter;
import com.example.liang.corange.dialog.ColorDialog;
import com.example.liang.corange.dialog.PromptDialog;
import com.example.liang.corange.model.HomeFragmentListModel;
import com.example.liang.corange.ui.BaseFragment;
import com.example.liang.corange.ui.BaseFragmentActivity;
import com.example.liang.corange.ui.TestActivity;
import com.example.liang.corange.utils.AnimationUtils;
import com.example.liang.corange.utils.DividerItemDecoration;
import com.example.liang.corange.views.TitleView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 首页
 * Created by liang on 1/8/2017.
 */

public class HomeFragment extends BaseFragment {
    private TitleView titleView;
    private XRecyclerView mXRecyclerView;
    private HomeFragmentListAdapter adapter;

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
        mXRecyclerView = (XRecyclerView)view.findViewById(R.id.xrecyclerview);
        mXRecyclerView.setPullRefreshEnabled(true);
        mXRecyclerView.setLoadingMoreEnabled(true);
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mXRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
        if(adapter==null){
            adapter = new HomeFragmentListAdapter(getActivity());
        }

        List<HomeFragmentListModel> list = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            HomeFragmentListModel model = new HomeFragmentListModel();
            model.setText("看吧"+i);
            list.add(model);
        }
        adapter.setData(list);
        mXRecyclerView.setAdapter(adapter);
    }
}
