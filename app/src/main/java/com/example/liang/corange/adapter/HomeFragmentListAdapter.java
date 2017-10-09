package com.example.liang.corange.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.liang.corange.R;
import com.example.liang.corange.model.HomeFragmentListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang on 2017/10/9.
 */

public class HomeFragmentListAdapter extends RecyclerView.Adapter<HomeFragmentListAdapter.ItemViewHold> {
    private Context context;
    private List<HomeFragmentListModel> data = new ArrayList<>();


    public HomeFragmentListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<HomeFragmentListModel> data) {
        this.data.clear();
        if (data != null) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }

    }

    public void addData(List<HomeFragmentListModel> data) {
        if (data != null && data.size() > 0) {
            this.data.addAll(data);
            notifyDataSetChanged();
        }


    }

    public void clearData() {
        this.data.clear();
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.fragment_home_list_item, parent, false);
        ItemViewHold itemViewHold = new ItemViewHold(itemView);
        return itemViewHold;
    }

    @Override
    public void onBindViewHolder(ItemViewHold holder, int position) {
        HomeFragmentListModel model = data.get(position);
        holder.textView.setText(model.getTime());
        holder.textView2.setText(model.getAddress());


    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ItemViewHold extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView textView2;

        public ItemViewHold(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            textView2 = (TextView) itemView.findViewById(R.id.text2);

        }
    }
}
