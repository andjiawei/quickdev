package com.netsite.quickdev.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.netsite.quickdev.R;

import java.util.ArrayList;

public abstract class BaseListActivity<T> extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected SampleListAdapter adapter;
    protected ArrayList<T> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpData() {
        mRecyclerView.setLayoutManager(getLayoutManager());
        RecyclerView.ItemDecoration decoration = getItemDecoration();
        if (decoration!=null) {
            mRecyclerView.addItemDecoration(decoration);
        }
        adapter = new SampleListAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getApplicationContext(), R.drawable.list_divider);

    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_sample_list, -1);
    }

    protected void setRefreshing() {
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


    public class SampleListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return getViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            holder.onBind(position);
        }

        @Override
        public int getItemCount() {
            return mDataList == null ? 0 : mDataList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return getItemType(position);
        }
    }
    protected int getItemType(int position) {
        return 0;
    }

    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);
}
