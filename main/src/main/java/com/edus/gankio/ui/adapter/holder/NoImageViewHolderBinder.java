package com.edus.gankio.ui.adapter.holder;

import com.edus.gankio.R;
import com.edus.gankio.browser.BrowserActivity;
import com.edus.gankio.data.CommonResource;
import com.edus.gankio.library.utils.DateUtils;
import com.edus.gankio.ui.adapter.multi.ViewHolderBinder;
import com.edus.gankio.ui.widget.recyclerview.DmBaseViewHolder;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 没有图片时的展示
 *
 * @author panda
 */
public class NoImageViewHolderBinder extends ViewHolderBinder<DmBaseViewHolder<CommonResource>, CommonResource> {

    @Override
    public DmBaseViewHolder<CommonResource> onCreateViewHolder(ViewGroup parentView) {
        return new NoImageViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(DmBaseViewHolder<CommonResource> viewHolder, CommonResource commonResource, int position) {
        viewHolder.updateData(commonResource, position);
    }


    public static class NoImageViewHolder extends DmBaseViewHolder<CommonResource>{
        private TextView mTvTitle;
        private TextView mTvSubTitle;
        private CommonResource mData;
        private TextView mTvDate;

        public NoImageViewHolder(ViewGroup parentView) {
            super(LayoutInflater.from(parentView.getContext()).inflate(R.layout.dm_viewholder_item_no_image, parentView, false));
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvSubTitle = itemView.findViewById(R.id.tv_subtitle);
            mTvDate = itemView.findViewById(R.id.tv_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mData != null && !TextUtils.isEmpty(mData.url)){
                        itemView.getContext().startActivity(BrowserActivity.makeIntent(itemView.getContext(), mData.url));
                    }
                }
            });
        }

        @Override
        public void updateData(CommonResource commonResource, int position) {
            mData = commonResource;
            if(mData != null){
                mTvTitle.setText(commonResource.desc);
                mTvSubTitle.setText(commonResource.who);
                String displayPublishAt = mData.getDisplayPublishAt();
                mTvDate.setText(DateUtils.friendlyTime(displayPublishAt));
            }
        }
    }


}
