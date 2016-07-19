package com.netsite.quickdev.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netsite.quickdev.R;
import com.netsite.quickdev.core.BaseActivity;

import java.util.ArrayList;

public class SampleListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private SampleListAdapter adapter;
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
    protected void setUpData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new SampleListAdapter();
        mRecyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    @Override
    protected void setUpView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);

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

    class SampleListAdapter extends RecyclerView.Adapter<SampleViewHolder>{

        @Override
        public SampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sample_list_item, parent, false);

            return new SampleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SampleViewHolder holder, int position) {
            holder.mSampleListItemLabel.setText(mDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataList==null?0:mDataList.size();
        }
    }

    class SampleViewHolder extends RecyclerView.ViewHolder{

        TextView mSampleListItemLabel;

        public SampleViewHolder(View itemView) {
            super(itemView);
            mSampleListItemLabel = (TextView) itemView.findViewById(R.id.mSampleListItemLabel);
        }
    }
}
