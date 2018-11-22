package com.edus.gankio.ui.adapter.holder;

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
import com.edus.gankio.data.CommonResource;
import com.edus.gankio.data.XianduData;
import com.edus.gankio.library.utils.DateUtils;
import com.edus.gankio.ui.adapter.multi.ViewHolderBinder;
import com.edus.gankio.ui.widget.recyclerview.DmBaseViewHolder;

/**
 * 只有一张/2张图片的展示
 *
 * @author panda
 */
public class SingleImageReadingViewHolderBinder extends ViewHolderBinder<DmBaseViewHolder<XianduData>, XianduData> {

    @Override
    public DmBaseViewHolder<XianduData> onCreateViewHolder(ViewGroup parentView) {
        return new SingleImageViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(DmBaseViewHolder<XianduData> viewHolder, XianduData commonResource, int position) {
        viewHolder.updateData(commonResource, position);
    }


    public static class SingleImageViewHolder extends DmBaseViewHolder<XianduData>{
        private ImageView mIvImage;
        private TextView mTvTitle;
        private TextView mTvSubTitle;
        private TextView mTvDate;
        private ImageView mIvSiteIcon;
        private XianduData mData;

        public SingleImageViewHolder(ViewGroup parentView) {
            super(LayoutInflater.from(parentView.getContext()).inflate(R.layout.dm_viewholder_xiandu_item_single_image, parentView, false));
            mIvImage = itemView.findViewById(R.id.iv_content);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvSubTitle = itemView.findViewById(R.id.tv_subtitle);
            mTvDate = itemView.findViewById(R.id.tv_date);
            mIvSiteIcon = itemView.findViewById(R.id.iv_site_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!TextUtils.isEmpty(mData.url)){
                        itemView.getContext().startActivity(BrowserActivity.makeIntent(itemView.getContext(), mData.url));
                    }
                }
            });
        }

        @Override
        public void updateData(XianduData data, int position) {
            mData = data;
            boolean matchedCover  = false;
            boolean matchedIcon  = false;
            if(mData != null){
                mTvTitle.setText(data.title);
                mTvSubTitle.setText(data.content);
                String displayPublishAt = data.getDisplayPublishAt();
                mTvDate.setText(DateUtils.friendlyTime(displayPublishAt));

                if(!TextUtils.isEmpty(mData.cover)){
                    Glide.with(itemView.getContext()).load(mData.cover)
                            .apply(RequestOptions.centerCropTransform()
                                    .placeholder(R.mipmap.ic_launcher)).into(mIvImage);
                    matchedCover = true;
                }
                if(mData.site != null && !TextUtils.isEmpty(mData.site.icon)){
                    mIvSiteIcon.setVisibility(View.VISIBLE);
                    Glide.with(itemView.getContext()).load(mData.site.icon)
                            .apply(RequestOptions.centerCropTransform()
                                    .placeholder(R.mipmap.ic_launcher)).into(mIvSiteIcon);
                    matchedIcon = true;
                }

            }
            if(!matchedCover){
                Glide.with(itemView.getContext()).load(R.mipmap.ic_launcher)
                        .apply(RequestOptions.centerCropTransform()
                                .placeholder(R.mipmap.ic_launcher)).into(mIvImage);
            }
            if(!matchedIcon){
                Glide.with(itemView.getContext()).load(R.mipmap.ic_launcher)
                        .apply(RequestOptions.centerCropTransform()
                                .placeholder(R.mipmap.ic_launcher)).into(mIvSiteIcon);
                mIvSiteIcon.setVisibility(View.GONE);
            }
        }
    }


}
