package com.edus.gankio.ui.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRvAdapter<VH extends RecyclerView.ViewHolder, DataType>  extends RecyclerView.Adapter<VH>{

    private List<DataType> mDataList = new ArrayList<>();

    public BaseRvAdapter(){

    }

    public final void setDataList(List<DataType> dataList){
        mDataList.clear();
        if(dataList != null && !dataList.isEmpty()){
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    public final DataType getDataTypeByPosition(int position){
        DataType data = null;
        if(position >= 0 && position < mDataList.size()){
            data = mDataList.get(position);
        }
        return data;
    }

    @Override
    public final int getItemCount() {
        return mDataList.size();
    }
}
