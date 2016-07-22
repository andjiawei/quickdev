package com.netsite.quickdev.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.netsite.quickdev.R;
import com.netsite.quickdev.widget.ILayoutManager;
import com.netsite.quickdev.widget.MyLinearLayoutManager;
import com.netsite.quickdev.widget.PullToRefreshRecycler;

import java.util.ArrayList;

public abstract class BaseListActivity<T> extends BaseActivity implements PullToRefreshRecycler.OnRecyclerRefreshListener {

    protected BaseListAdapter adapter;
    protected ArrayList<T> mDataList;
    protected PullToRefreshRecycler recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpData() {

        recycler.setOnRefreshListener(this);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addItemDecoration(getItemDecoration());
        adapter = new BaseListAdapter();
        recycler.setAdapter(adapter);
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getApplicationContext(), R.drawable.list_divider);

    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_sample_list, -1);
    }

    @NonNull
    private ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getApplicationContext());
    }

    @Override
    protected void setUpView() {
        recycler = (PullToRefreshRecycler) findViewById(R.id.pullToRefreshRecycler);
    }

    public class BaseListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

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
