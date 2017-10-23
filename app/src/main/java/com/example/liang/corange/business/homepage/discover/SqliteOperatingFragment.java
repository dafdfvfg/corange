package com.example.liang.corange.business.homepage.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liang.corange.R;
import com.example.liang.corange.db.DiscoverDBHelp;
import com.example.liang.corange.ui.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liang on 2017/10/18.
 */

public class SqliteOperatingFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sqlite_operating, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @OnClick(R.id.button1)
    public void setButton1() {
        DiscoverDBHelp.getInstance();

    }

    @OnClick(R.id.button2)
    public void setButton2() {

    }

    @OnClick(R.id.button3)
    public void setButton3() {

    }

    @OnClick(R.id.button4)
    public void setButton4() {

    }

    @OnClick(R.id.button5)
    public void setButton5() {

    }

    @OnClick(R.id.button6)
    public void setButton6() {

    }

    @OnClick(R.id.button7)
    public void setButton7() {

    }
}
