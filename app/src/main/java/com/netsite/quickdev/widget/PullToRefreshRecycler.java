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


    private static final int ACTION_IDLE =0;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    public static final int ACTION_PULL_TO_REFRESH = 1;
    public static final int ACTION_LOAD_MORE_REFRESH = 2;
    private boolean isLoadMoreEnabled = false;
    private boolean isPullToRefreshEnabled = true;
    private int mCurrentState;

    private ILayoutManager mLayoutManager;

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
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //如果没在刷新 & 可加载更多 & 到达底部 就加载更多
                if(mCurrentState == ACTION_IDLE && isLoadMoreEnabled && checkIfLoadMore()){
                    mCurrentState = ACTION_LOAD_MORE_REFRESH;//改变状态为加载更多 防止onScrolled多次调用
                    mSwipeRefreshLayout.setEnabled(false);//加载更多时下拉刷新不可用
                    listener.onRefresh(ACTION_LOAD_MORE_REFRESH);//开始加载更多
                }
            }
        });
    }

    private boolean checkIfLoadMore() {
//        TODO check if scroll to bottom
        int lastVisibleItemPosition = mLayoutManager.findLastVisiblePosition();
        int totalCount = mLayoutManager.getLayoutManager().getItemCount();
        return totalCount - lastVisibleItemPosition < 5;
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager){
        mRecyclerView.setLayoutManager(manager);
    }

    public void setLayoutManager(ILayoutManager manager) {
        this.mLayoutManager = manager;
        mRecyclerView.setLayoutManager(manager.getLayoutManager());
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

    public void enableLoadMore(boolean enable) {
        isLoadMoreEnabled = enable;
    }

    public void enablePullToRefresh(boolean enable) {
        isPullToRefreshEnabled = enable;
    }

    @Override
    public void onRefresh() {
        mCurrentState = ACTION_PULL_TO_REFRESH;
        listener.onRefresh(ACTION_PULL_TO_REFRESH);
    }

    public void onRefreshCompleted() {
        switch (mCurrentState) {
            case ACTION_PULL_TO_REFRESH:
                mSwipeRefreshLayout.setRefreshing(false);//刷新完成隐藏圆圈
                break;
            case ACTION_LOAD_MORE_REFRESH:
                if (isPullToRefreshEnabled) {
                    mSwipeRefreshLayout.setEnabled(true);//加载完 重启下拉刷新
                }
                break;
        }
        mCurrentState = ACTION_IDLE;//完成 重置状态为空闲
    }

    private OnRecyclerRefreshListener listener;
    public void setOnRefreshListener(OnRecyclerRefreshListener listener){
        this.listener = listener;
    }

    public interface OnRecyclerRefreshListener{
        void onRefresh(int action);
    }
}
