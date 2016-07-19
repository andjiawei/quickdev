package com.netsite.quickdev.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.netsite.quickdev.R;
import com.netsite.quickdev.core.BaseActivity;

public abstract class BaseListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected SampleListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpData() {
        mRecyclerView.setLayoutManager(getLayoutManager());
        adapter = new SampleListAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    protected void setRefreshing(){
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    @NonNull
    private LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(getApplicationContext());
    }

    @Override
    protected void setUpView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);

    }


    public class SampleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return getViewHolder(parent,viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           onBind(holder,position);
        }

        @Override
        public int getItemCount() {
            return getCount();
        }
    }

    protected abstract void onBind(RecyclerView.ViewHolder holder, int position);

    protected abstract int getCount();

    protected abstract RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) ;
}
