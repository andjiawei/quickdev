package com.netsite.quickdev.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netsite.quickdev.R;

import java.util.ArrayList;

public class SampleListActivity extends BaseListActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<String> mDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_sample_list, R.string.sample_list_title);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
    }

    @Override
    protected void setUpData() {
        super.setUpData();
        setRefreshing();
    }


    @Override
    public void onRefresh() {
        mDataList.clear();
        for (int i = 0; i < 50; i++) {
            mDataList.add("sample list item " + i);
        }
        adapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        ((SampleViewHolder)holder).mSampleListItemLabel.setText(mDataList.get(position));
    }

    @Override
    protected int getCount() {
        return  mDataList==null?0:mDataList.size();
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sample_list_item, parent, false);
        return new SampleViewHolder(view);
    }

    class SampleViewHolder extends RecyclerView.ViewHolder{

        TextView mSampleListItemLabel;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mSampleListItemLabel = (TextView) itemView.findViewById(R.id.mSampleListItemLabel);
        }
    }
}
