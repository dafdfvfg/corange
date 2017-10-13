package com.example.liang.corange.business.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.liang.corange.R;
import com.example.liang.corange.ui.BaseFragment;
import com.example.liang.corange.views.TitleView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 发现
 * Created by liang on 1/8/2017.
 */

public class DiscoverFragment extends BaseFragment {
    @BindView(R.id.title_view)
    TitleView titleView;
    @BindView(R.id.btn_save)
    Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover_main, null);
        ButterKnife.bind(this, view);
        titleView.setLeftDrawableVisible(false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.btn_save)
    public void sumBut(Button button) {
        button.setText("真是人才呀");
    }
}
