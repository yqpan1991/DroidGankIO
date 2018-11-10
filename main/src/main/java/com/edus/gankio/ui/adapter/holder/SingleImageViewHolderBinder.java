package com.edus.gankio.ui.adapter.holder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.edus.gankio.R;
import com.edus.gankio.data.CommonResource;
import com.edus.gankio.ui.adapter.multi.ViewHolderBinder;
import com.edus.gankio.ui.widget.recyclerview.DmBaseViewHolder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 只有一张图片或者没有图片时的展示,如果没有图片,设置默认图即可
 *
 * @author panda
 */
public class SingleImageViewHolderBinder extends ViewHolderBinder<DmBaseViewHolder<CommonResource>, CommonResource> {

    @Override
    public DmBaseViewHolder<CommonResource> onCreateViewHolder(ViewGroup parentView) {
        return new SingleImageViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(DmBaseViewHolder<CommonResource> viewHolder, CommonResource commonResource, int position) {
        viewHolder.updateData(commonResource, position);
    }


    public static class SingleImageViewHolder extends DmBaseViewHolder<CommonResource>{
        private ImageView mIvImage;
        private TextView mTvTitle;
        private TextView mTvSubTitle;
        private CommonResource mData;

        public SingleImageViewHolder(ViewGroup parentView) {
            super(LayoutInflater.from(parentView.getContext()).inflate(R.layout.dm_viewholder_item_single_image, parentView, false));
            mIvImage = itemView.findViewById(R.id.iv_content);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvSubTitle = itemView.findViewById(R.id.tv_subtitle);
        }

        @Override
        public void updateData(CommonResource commonResource, int position) {
            mData = commonResource;
            boolean matched  = false;
            if(mData != null){
                mTvTitle.setText(commonResource.desc);
                mTvSubTitle.setText(commonResource.who);
                if(mData.images != null && !mData.images.isEmpty() && !TextUtils.isEmpty(mData.images.get(0))){
                    matched = true;
                    Glide.with(itemView.getContext()).load(mData.images.get(0))
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
