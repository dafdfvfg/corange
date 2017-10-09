package com.example.liang.corange.business.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liang.corange.R;
import com.example.liang.corange.ui.BaseFragment;
import com.example.liang.corange.views.TitleView;

/**
 * 发现
 * Created by liang on 1/8/2017.
 */

public class DiscoverFragment extends BaseFragment {
    private TitleView titleView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover_main, null);
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
    }
}
