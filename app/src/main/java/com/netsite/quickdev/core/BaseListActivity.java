package com.netsite.quickdev.core;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
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
    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getApplicationContext());
    }

    @Override
    protected void setUpView() {
        recycler = (PullToRefreshRecycler) findViewById(R.id.pullToRefreshRecycler);
    }

    public class BaseListAdapter extends RecyclerView.Adapter<BaseViewHolder> {
        private static final int VIEW_TYPE_LOAD_MORE_FOOTER = 100;
        private boolean isShowLoadMoreFooter;

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == VIEW_TYPE_LOAD_MORE_FOOTER) {
                return getLoadMoreFooter(parent);
            }

            return getViewHolder(parent, viewType);
        }

        protected BaseViewHolder getLoadMoreFooter(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_pull_to_refresh_footer, parent, false);
            return new LoadMoreFooterViewHolder(view);
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {

            if(isShowLoadMoreFooter && position == getItemCount() - 1){
                //对瀑布流的特殊处理
                if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                    params.setFullSpan(true);//最后一条设置full
                }
            }
            holder.onBindViewHolder(position);
        }

        public void showLoadMoreFooter(boolean isShow) {
            this.isShowLoadMoreFooter = isShow;
            if (isShow) {
                notifyItemInserted(getItemCount());
            } else {
                notifyItemRemoved(getItemCount());
            }
        }

        @Override
        public int getItemCount() {
            return mDataList != null ? mDataList.size() + (isShowLoadMoreFooter ? 1 : 0) : 0;
        }

        @Override
        public int getItemViewType(int position) {

            if (isShowLoadMoreFooter && position == getItemCount() - 1) {
                return VIEW_TYPE_LOAD_MORE_FOOTER;
            }

            return getItemType(position);
        }

        private class LoadMoreFooterViewHolder extends BaseViewHolder {
            public LoadMoreFooterViewHolder(View view) {
                super(view);
            }

            @Override
            public void onBindViewHolder(int position) {

            }
        }

        public boolean isFooterView(int position) {
            return isShowLoadMoreFooter && position == getItemCount() - 1;
        }
    }
    protected int getItemType(int position) {
        return 0;
    }

    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);
}
