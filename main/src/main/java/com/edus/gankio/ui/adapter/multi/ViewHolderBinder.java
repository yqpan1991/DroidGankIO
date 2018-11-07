package com.edus.gankio.ui.adapter.multi;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface ViewHolderBinder<ViewHolder extends RecyclerView.ViewHolder, Data> {
    ViewHolder onCreateViewHolder(ViewGroup parentView);
    void onBindViewHolder(ViewHolder viewHolder, Data data, int position);
}
