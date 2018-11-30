package com.edus.gankio.ui.adapter;

import com.edus.gankio.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description.
 *
 * @author panda
 */
public class BrowserShareAdapter extends BaseAdapter {
    private ArrayList<SelectItem> mArrayList;

    public BrowserShareAdapter(){
        mArrayList = new ArrayList<>();
    }

    public void setDataList(List<SelectItem> dataList){
        mArrayList.clear();
        if(dataList != null && !dataList.isEmpty()){
            mArrayList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public SelectItem getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.browser_adapter_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTvContent = convertView.findViewById(R.id.tv_content);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SelectItem item = getItem(position);
        if(item != null){
            viewHolder.mTvContent.setText(item.tips);
        }else{
            viewHolder.mTvContent.setText(null);
        }
        return convertView;
    }

    private static class ViewHolder{
        private TextView mTvContent;
    }

    public static class SelectItem{
        public int type;
        public String tips;
    }
}
