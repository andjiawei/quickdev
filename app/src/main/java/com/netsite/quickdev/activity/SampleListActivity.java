package com.netsite.quickdev.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netsite.quickdev.R;
import com.netsite.quickdev.core.BaseListActivity;
import com.netsite.quickdev.core.BaseViewHolder;
import com.netsite.quickdev.widget.PullToRefreshRecycler;

import java.util.ArrayList;

public class SampleListActivity extends BaseListActivity<String> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void setUpTitle(int titleResId) {
        super.setUpTitle(R.string.sample_list_title);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
    }

    @Override
    protected void setUpData() {
        super.setUpData();
        recycler.setRefreshing();
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sample_list_item, parent, false);
        return new SampleViewHolder(view);
    }

    @Override
    public void onRefresh(int action) {

        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        if (action == PullToRefreshRecycler.ACTION_PULL_TO_REFRESH) {
            mDataList.clear();
        }
        int size = mDataList.size();
        for (int i = size; i < size + 20; i++) {
            mDataList.add("sample list item " + i);
        }
        adapter.notifyDataSetChanged();
        recycler.onRefreshCompleted();
        if (mDataList.size() < 100) {
            recycler.enableLoadMore(true);
        } else {
            recycler.enableLoadMore(false);
        }
    }

    class SampleViewHolder extends BaseViewHolder{

        TextView mSampleListItemLabel;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mSampleListItemLabel = (TextView) itemView.findViewById(R.id.mSampleListItemLabel);
        }

        @Override
        protected void onBind(int position) {
            mSampleListItemLabel.setText(mDataList.get(position));
        }
    }
}
