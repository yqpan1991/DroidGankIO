package com.edus.gankio.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edus.gankio.R;
import com.edus.gankio.data.CommonResource;
import com.edus.gankio.ui.adapter.multi.SubTypeLinker;
import com.edus.gankio.ui.adapter.multi.TypePoolImpl;
import com.edus.gankio.ui.adapter.multi.ViewHolderBinder;
import com.edus.gankio.ui.widget.recyclerview.DmBaseAdapter;
import com.edus.gankio.ui.widget.recyclerview.DmBaseViewHolder;

public class HomePageBaseAdapter<DataType> extends DmBaseAdapter<DataType> {
    private TypePoolImpl mTypePool;

    public HomePageBaseAdapter(Context context){
        super(context);
        mTypePool = new TypePoolImpl(0, this);
    }

    public <T> void registerType(Class<T> clz, ViewHolderBinder<? extends RecyclerView.ViewHolder, T> viewHolderBinder){
        mTypePool.registerType(clz, viewHolderBinder);
    }

    public <T> void registerMultiType(Class<T> clz, SubTypeLinker<T> subTypeLinker){
        mTypePool.registerMultiType(clz, subTypeLinker);
    }

    @Override
    public int getAdapterItemViewType(int dataListPosition) {
        return mTypePool.getItemViewType(getAdapterDataItem(dataListPosition));
    }

    @Override
    public DmBaseViewHolder<DataType> onCreateAdapterViewHolder(ViewGroup parent, int viewType) {
        return (DmBaseViewHolder<DataType>) mTypePool.onCreateViewHolder(viewType, parent);
    }

    @Override
    public void onBindAdapterViewHolder(DmBaseViewHolder<DataType> holder, int dataListPosition) {
        mTypePool.onBindViewHolder(holder, getAdapterDataItem(dataListPosition), dataListPosition);
    }

}
