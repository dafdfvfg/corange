package com.example.liang.corange.business.homepage;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liang.corange.R;
import com.example.liang.corange.network.HttpUtils;
import com.example.liang.corange.ui.BaseFragment;
import com.example.liang.corange.ui.BaseFragmentActivity;
import com.example.liang.corange.ui.TestActivity;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 我的页面
 * Created by liang on 1/8/2017.
 */

public class MineFragment extends BaseFragment {
    private Unbinder unbinder;
    @BindView(R.id.text)
    TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_main, null);
        unbinder = ButterKnife.bind(this, view);
        return view;



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        textView.setText("rencaiya");
        Map<String, String> map = new HashMap<>();
        map.put("key", "bc269be612b2e20cb75dcc8451237905");
        map.put("v", "1.0");
        map.put("month", 4 + "");
        map.put("day", 21 + "");
        Log.d("wocao", "json is:" + map.toString());
        HttpUtils.doGet("http://api.juheapi.com/japi/toh", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Log.d("wocao", "失败?" + e.toString());
//                Toast.makeText(getActivity(),"人才呀",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.d("wocao", "成功?" + response.toString());
//                Toast.makeText(getActivity(),"人才呀",Toast.LENGTH_LONG).show();
            }
        });
    }


//    @OnClick(R.id.text)
//    public void setOnClik() {
//        startActivity(new Intent(getActivity(),TestActivity.class));
//
//        FragmentManager manager = getFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        TestFragment fragment = new TestFragment();
//        transaction.add(R.id.main,fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//
//        BaseFragmentActivity.startFragment(getActivity(), TestFragment.class, null);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
