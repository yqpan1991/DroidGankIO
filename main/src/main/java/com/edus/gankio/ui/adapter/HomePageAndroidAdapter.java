package com.edus.gankio.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edus.gankio.R;
import com.edus.gankio.data.CommonResource;
import com.edus.gankio.data.CommonResult;

import java.util.ArrayList;
import java.util.List;

public class HomePageAndroidAdapter extends BaseRvAdapter<HomePageAndroidAdapter.ViewHolder, CommonResource> {
    public HomePageAndroidAdapter(){

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommonResource data = getDataTypeByPosition(position);
        holder.refresh(data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvContent;
        private CommonResource mData;


        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_adapter_item, parent, false));
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content);

        }
        public void refresh(CommonResource data){
            mData = data;
            mTvContent.setText(mData.desc);
        }
    }
}
