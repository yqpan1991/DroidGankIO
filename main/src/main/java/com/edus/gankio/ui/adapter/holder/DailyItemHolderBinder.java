package com.edus.gankio.ui.adapter.holder;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.edus.gankio.R;
import com.edus.gankio.browser.BrowserActivity;
import com.edus.gankio.data.DailyItem;
import com.edus.gankio.library.utils.DateUtils;
import com.edus.gankio.ui.adapter.multi.ViewHolderBinder;
import com.edus.gankio.ui.widget.recyclerview.DmBaseViewHolder;

public class DailyItemHolderBinder extends ViewHolderBinder<DailyItemHolderBinder.DailyItemHolder, DailyItem> {

    @Override
    public DailyItemHolder onCreateViewHolder(ViewGroup parentView) {
        return new DailyItemHolder(parentView);
    }

    @Override
    public void onBindViewHolder(DailyItemHolder viewHolder, DailyItem dailyItem, int position) {
        viewHolder.updateData(dailyItem, position);
    }

    public static class DailyItemHolder extends DmBaseViewHolder<DailyItem>{


        private ImageView mIvImage;
        private TextView mTvTitle;
        private TextView mTvSubTitle;
        private TextView mTvDate;
        private DailyItem mData;

        public DailyItemHolder(ViewGroup parentView) {
            super(LayoutInflater.from(parentView.getContext()).inflate(R.layout.dm_viewholder_item_daily_item, parentView, false));
            mIvImage = itemView.findViewById(R.id.iv_content);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvSubTitle = itemView.findViewById(R.id.tv_subtitle);
            mTvDate = itemView.findViewById(R.id.tv_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!TextUtils.isEmpty(mData.content)){
                        BrowserActivity.startMe(v.getContext(), mData.content);
                    }
                }
            });
        }

        @Override
        public void updateData(DailyItem commonResource, int position) {
            mData = commonResource;
            boolean matched  = false;
            if(mData != null){
                mTvTitle.setText(commonResource.title);
                String displayPublishAt = commonResource.getDisplayPublishAt();
                mTvDate.setText(DateUtils.friendlyTime(displayPublishAt));
                String imgUrl = mData.getImgUrl();
                if(!TextUtils.isEmpty(imgUrl)){
                    matched = true;
                    Glide.with(itemView.getContext()).load(imgUrl)
                            .apply(RequestOptions.centerCropTransform()
                                    .placeholder(R.mipmap.ic_launcher)).into(mIvImage);
                }

            }
            if(!matched){
                Glide.with(itemView.getContext()).load(R.mipmap.ic_launcher)
                        .apply(RequestOptions.centerCropTransform()
                                .placeholder(R.mipmap.ic_launcher)).into(mIvImage);
            }
        }
    }
}
