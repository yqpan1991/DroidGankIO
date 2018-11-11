package com.edus.gankio.ui.adapter.multi;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface TypePool<Adapter extends RecyclerView.Adapter> {
    int getItemViewType(Object t);

    RecyclerView.ViewHolder onCreateViewHolder(int itemViewType, ViewGroup parent);

    void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Object t, int position);

    <T> void registerType(Class<T> clz, ViewHolderBinder<? extends RecyclerView.ViewHolder, T> viewHolderBinder);

    <T> void registerMultiType(Class<T> clz, SubTypeLinker<T> subTypeLinker);

}
