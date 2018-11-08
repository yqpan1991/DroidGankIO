package com.edus.gankio.ui.adapter.multi;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * ViewHolderCreater
 * @param <ViewHolder>
 * @param <Data>
 */
public abstract class ViewHolderBinder<ViewHolder extends RecyclerView.ViewHolder, Data> {
    RecyclerView.Adapter mAdapter;

    public abstract ViewHolder onCreateViewHolder(ViewGroup parentView);
    public abstract void onBindViewHolder(ViewHolder viewHolder, Data data, int position);

    protected RecyclerView.Adapter getAdapter(){
        return mAdapter;
    }
}
