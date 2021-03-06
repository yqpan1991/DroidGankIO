package com.edus.gankio.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edus.gankio.R;


public class HomePageHomeAdapter extends BaseRvAdapter<HomePageHomeAdapter.ViewHolder, String> {
    public HomePageHomeAdapter(){

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String data = getDataTypeByPosition(position);
        holder.refresh(data);
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
