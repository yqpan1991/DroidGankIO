package com.edus.gankio.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edus.gankio.R;

import java.util.ArrayList;
import java.util.List;

public class HomePageHomeAdapter extends RecyclerView.Adapter<HomePageHomeAdapter.ViewHolder> {
    private List<String> mDataList = new ArrayList<>();

    public HomePageHomeAdapter(){

    }

    public void setDataList(List<String> dataList){
        mDataList.clear();
        if(dataList != null && !dataList.isEmpty()){
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String data = null;
        if(position >= 0 && position < mDataList.size()){
            data = mDataList.get(position);
        }
        holder.refresh(data);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvContent;
        private String mData;


        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_adapter_item, parent, false));
            mTvContent = (TextView) itemView.findViewById(R.id.tv_content);

        }
        public void refresh(String data){
            mData = data;
            mTvContent.setText(mData);
        }
    }
}
