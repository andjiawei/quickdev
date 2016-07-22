package com.netsite.quickdev.widget;

import android.support.v7.widget.RecyclerView;

public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();
    int findLastVisiblePosition();
}
