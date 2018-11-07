package com.edus.gankio.ui.adapter.multi;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface TypePool {
    int getItemViewType(Object t);

    RecyclerView.ViewHolder onCreateViewHolder(int itemViewType, ViewGroup parent);

    void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Object t, int position);

    void registerType(Class<?> clz, ViewHolderBinder viewHolderBinder);

    void registerMultiType(Class<?> clz, SubTypeLinker<?> subTypeLinker);

}
