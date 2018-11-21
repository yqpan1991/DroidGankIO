package com.edus.gankio.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edus.gankio.R;
import com.edus.gankio.data.XianduCategoryItem;
import com.edus.gankio.data.XianduSubCategoryItem;

import java.util.ArrayList;
import java.util.List;

public class CategorySubSelectAdapter extends RecyclerView.Adapter<CategorySubSelectAdapter.CategoryViewHolder> {

    private List<XianduSubCategoryItem> mDataList;
    private int mSelectedPos;
    private OnItemSelectListener mSelectListener;

    public CategorySubSelectAdapter(){
        mDataList = new ArrayList<>();
        mSelectedPos = -1;
    }

    public void setCategoryList(List<XianduSubCategoryItem> dataList){
        mDataList.clear();
        if(dataList != null && !dataList.isEmpty()){
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    public void setSelectListener(OnItemSelectListener mSelectListener) {
        this.mSelectListener = mSelectListener;
    }

    private OnItemSelectListener mWrapClickListener = new OnItemSelectListener() {
        @Override
        public void onItemSelected(int selectedPosition) {
            mSelectedPos = selectedPosition;
            notifyDataSetChanged();
            if(mSelectListener != null){
                mSelectListener.onItemSelected(selectedPosition);
            }
        }
    };

    public XianduSubCategoryItem getItem(int position){
        if(position < 0 || position >= mDataList.size()){
            return null;
        }
        return mDataList.get(position);
    }

    public void setSelectedPosition(int position){
        if(mSelectedPos != position){
            mSelectedPos = position;
            notifyDataSetChanged();
        }
    }

    public int getSelectedPos(){
        return mSelectedPos;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(parent, mWrapClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.updateData(mDataList.get(position), position, position == mSelectedPos);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        private int mPosition;
        private XianduSubCategoryItem mItem;
        private boolean mSelected;
        private TextView mTvContent;
        private OnItemSelectListener mOnItemSelectListener;

        public CategoryViewHolder(ViewGroup parentView, OnItemSelectListener onItemSelectListener) {
            super(LayoutInflater.from(parentView.getContext()).inflate(R.layout.dm_viewholder_item_category_item, parentView, false));
            mTvContent = itemView.findViewById(R.id.tv_title);
            mOnItemSelectListener = onItemSelectListener;
            mTvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemSelectListener != null){
                        mOnItemSelectListener.onItemSelected(mPosition);
                    }
                }
            });
        }

        public void updateData(XianduSubCategoryItem item, int position, boolean isSelected){
            mPosition = position;
            mItem = item;
            mSelected = isSelected;

            mTvContent.setText(item.title);
            mTvContent.setSelected(isSelected);
        }


    }
}
