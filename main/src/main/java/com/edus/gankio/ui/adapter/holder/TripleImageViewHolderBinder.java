package com.edus.gankio.ui.adapter.holder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.edus.gankio.R;
import com.edus.gankio.browser.BrowserActivity;
import com.edus.gankio.data.CommonResource;
import com.edus.gankio.library.utils.DateUtils;
import com.edus.gankio.library.utils.DensityUtils;
import com.edus.gankio.ui.adapter.multi.ViewHolderBinder;
import com.edus.gankio.ui.widget.recyclerview.DmBaseViewHolder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 三张图片或者更多图片的展示
 *
 * @author panda
 */
public class TripleImageViewHolderBinder extends ViewHolderBinder<DmBaseViewHolder<CommonResource>, CommonResource> {

    @Override
    public DmBaseViewHolder<CommonResource> onCreateViewHolder(ViewGroup parentView) {
        return new TripleImageViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(DmBaseViewHolder<CommonResource> viewHolder, CommonResource commonResource, int position) {
        viewHolder.updateData(commonResource, position);
    }


    public static class TripleImageViewHolder extends DmBaseViewHolder<CommonResource>{
        private ImageView mIvImage1;
        private ImageView mIvImage2;
        private ImageView mIvImage3;
        private TextView mTvTitle;
        private TextView mTvSubTitle;
        private TextView mTvDate;
        private CommonResource mData;
        private int mItemWidth;

        public TripleImageViewHolder(ViewGroup parentView) {
            super(LayoutInflater.from(parentView.getContext()).inflate(R.layout.dm_viewholder_item_tripple_image, parentView, false));
            mIvImage1 = itemView.findViewById(R.id.iv_image1);
            mIvImage2 = itemView.findViewById(R.id.iv_image2);
            mIvImage3 = itemView.findViewById(R.id.iv_image3);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvSubTitle = itemView.findViewById(R.id.tv_subtitle);
            mTvDate = itemView.findViewById(R.id.tv_date);
            mItemWidth = DensityUtils.getScreenWidth(parentView.getContext())/3 - DensityUtils.dp2Px(parentView.getContext(), 10);

            checkResize(mIvImage1);
            checkResize(mIvImage2);
            checkResize(mIvImage3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mData != null && !TextUtils.isEmpty(mData.url)){
                        itemView.getContext().startActivity(BrowserActivity.makeIntent(itemView.getContext(), mData.url));
                    }
                }
            });

        }

        private void checkResize(ImageView imageView) {
            if(imageView == null){
                return;
            }
            if(imageView.getLayoutParams() != null){
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                if(layoutParams.width != mItemWidth){
                    layoutParams.width = mItemWidth;
                    layoutParams.height = mItemWidth;
                }
            }
        }

        @Override
        public void updateData(CommonResource commonResource, int position) {
            mData = commonResource;
            if(mData != null){
                mTvTitle.setText(commonResource.desc);
                mTvSubTitle.setText(commonResource.who);
                mTvDate.setText(DateUtils.friendlyTime(commonResource.getDisplayPublishAt()));
            }


            if(mData != null && mData.images != null && mData.images.size() >= 3){
                Glide.with(itemView.getContext()).load(mData.images.get(0))
                        .apply(RequestOptions.centerCropTransform()
                                .placeholder(R.mipmap.ic_launcher)).into(mIvImage1);
                Glide.with(itemView.getContext()).load(mData.images.get(1))
                        .apply(RequestOptions.centerCropTransform()
                                .placeholder(R.mipmap.ic_launcher)).into(mIvImage2);
                Glide.with(itemView.getContext()).load(mData.images.get(2))
                        .apply(RequestOptions.centerCropTransform()
                                .placeholder(R.mipmap.ic_launcher)).into(mIvImage3);

            }else{
                Glide.with(itemView.getContext()).load(R.mipmap.ic_launcher)
                        .apply(RequestOptions.centerCropTransform()
                                .placeholder(R.mipmap.ic_launcher)).into(mIvImage1);
                Glide.with(itemView.getContext()).load(R.mipmap.ic_launcher)
                        .apply(RequestOptions.centerCropTransform()
                                .placeholder(R.mipmap.ic_launcher)).into(mIvImage2);
                Glide.with(itemView.getContext()).load(R.mipmap.ic_launcher)
                        .apply(RequestOptions.centerCropTransform()
                                .placeholder(R.mipmap.ic_launcher)).into(mIvImage3);
            }


        }
    }


}
