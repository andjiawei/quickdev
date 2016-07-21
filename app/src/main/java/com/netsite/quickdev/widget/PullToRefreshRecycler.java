package com.netsite.quickdev.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.netsite.quickdev.R;

/**
 * Created by QYer on 2016/7/20.
 */
public class PullToRefreshRecycler extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    public static final int ACTION_PULL_TO_REFRESH = 1;
    public static final int ACTION_LOAD_MORE_REFRESH = 2;

    public PullToRefreshRecycler(Context context) {
        super(context);
        setUpView();
    }

    public PullToRefreshRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpView();
    }

    public PullToRefreshRecycler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView();
    }

    private void setUpView() {

        LayoutInflater.from(getContext()).inflate(R.layout.widget_pull_to_refresh,this,true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager){
        mRecyclerView.setLayoutManager(manager);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration){
        if (decoration != null){
            mRecyclerView.addItemDecoration(decoration);
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        mRecyclerView.setAdapter(adapter);
    }

    public void setRefreshing(){
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    private int mCurrentState;
    @Override
    public void onRefresh() {
        mCurrentState = ACTION_PULL_TO_REFRESH;
        listener.onRefresh(ACTION_PULL_TO_REFRESH);
    }

    public void onRefreshCompleted() {
        switch (mCurrentState){
            case ACTION_PULL_TO_REFRESH://下拉刷新完成隐藏 圆圈
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case ACTION_LOAD_MORE_REFRESH:
                break;
        }
    }

    private OnRecyclerRefreshListener listener;
    public void setOnRefreshListener(OnRecyclerRefreshListener listener){
        this.listener = listener;
    }

    public interface OnRecyclerRefreshListener{
        void onRefresh(int action);
    }
}
