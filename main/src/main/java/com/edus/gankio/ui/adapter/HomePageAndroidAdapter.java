package com.edus.gankio.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edus.gankio.R;
import com.edus.gankio.data.CommonResource;
import com.edus.gankio.data.CommonResult;
import com.edus.gankio.ui.adapter.multi.SubTypeLinker;
import com.edus.gankio.ui.adapter.multi.TypePoolImpl;
import com.edus.gankio.ui.adapter.multi.ViewHolderBinder;
import com.edus.gankio.ui.widget.recyclerview.DmBaseAdapter;
import com.edus.gankio.ui.widget.recyclerview.DmBaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HomePageAndroidAdapter extends DmBaseAdapter<CommonResource> {
    private TypePoolImpl mTypePool;

    public HomePageAndroidAdapter(Context context){
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
    public DmBaseViewHolder<CommonResource> onCreateAdapterViewHolder(ViewGroup parent, int viewType) {
        return (DmBaseViewHolder<CommonResource>) mTypePool.onCreateViewHolder(viewType, parent);
    }

    @Override
    public void onBindAdapterViewHolder(DmBaseViewHolder<CommonResource> holder, int dataListPosition) {
        mTypePool.onBindViewHolder(holder, getAdapterDataItem(dataListPosition), dataListPosition);
    }

    public static class ViewHolder extends DmBaseViewHolder<CommonResource> {
        private TextView mTvContent;
        private CommonResource mData;


        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_adapter_item, parent, false));
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content);

        }

        @Override
        public void updateData(CommonResource data, int position){
            mData = data;
            if(mData != null){
                mTvContent.setText(mData.desc);
            }else{
                mTvContent.setText(null);
            }
        }
    }
}
