package com.netsite.quickdev.core;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by QYer on 2016/7/20.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder(int position);

}