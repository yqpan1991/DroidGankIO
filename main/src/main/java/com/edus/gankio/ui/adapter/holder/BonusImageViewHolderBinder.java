package com.edus.gankio.ui.adapter.holder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apollo.edus.uilibrary.widget.AspectRatioImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.edus.gankio.R;
import com.edus.gankio.data.CommonResource;
import com.edus.gankio.library.utils.DensityUtils;
import com.edus.gankio.ui.adapter.multi.ViewHolderBinder;
import com.edus.gankio.ui.widget.recyclerview.DmBaseViewHolder;

public class BonusImageViewHolderBinder extends ViewHolderBinder<BonusImageViewHolderBinder.BonusImageViewHolder, CommonResource> {
    @Override
    public BonusImageViewHolder onCreateViewHolder(ViewGroup parentView) {
        return new BonusImageViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(BonusImageViewHolder viewHolder, CommonResource commonResource, int position) {
        viewHolder.updateData(commonResource, position);
    }

    public static class BonusImageViewHolder extends DmBaseViewHolder<CommonResource> {
        private AspectRatioImageView mIvImage;
        private int mItemWidth = 0;
        private View mVTopSpace;
        private View mVBottomSpace;

        public BonusImageViewHolder(ViewGroup parentView) {
            super(LayoutInflater.from(parentView.getContext()).inflate(R.layout.dm_viewholder_item_bonus_image, parentView, false));
            mIvImage = itemView.findViewById(R.id.iv_image);
            mVTopSpace = itemView.findViewById(R.id.v_top_space);
            mVBottomSpace = itemView.findViewById(R.id.v_bottom_space);
            mItemWidth = DensityUtils.getScreenWidth(parentView.getContext()) /2 - DensityUtils.dp2Px(parentView.getContext(), 20);
            if(mIvImage.getLayoutParams() != null){
                ViewGroup.LayoutParams layoutParams = mIvImage.getLayoutParams();
                if(layoutParams.width != mItemWidth){
                    layoutParams.width =  mItemWidth;
                }
            }
        }

        @Override
        public void updateData(CommonResource commonResource, int position) {
            if(commonResource == null || TextUtils.isEmpty(commonResource.url)){
                Glide.with(itemView.getContext()).load(R.mipmap.ic_launcher)
                        .apply(RequestOptions.centerCropTransform()
                                .placeholder(R.mipmap.ic_launcher)).into(mIvImage);
            }else{
                Glide.with(itemView.getContext()).load(commonResource.url)
                        .apply(RequestOptions.centerCropTransform()
                                .placeholder(R.mipmap.ic_launcher)).into(mIvImage);
            }
            if(position % 2 ==0){
                mVTopSpace.setVisibility(View.GONE);
                mVBottomSpace.setVisibility(View.VISIBLE);
            }else{
                mVTopSpace.setVisibility(View.VISIBLE);
                mVBottomSpace.setVisibility(View.GONE);
            }
        }
    }

}
