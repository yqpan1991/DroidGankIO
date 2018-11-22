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
 * 没有图片时的展示
 *
 * @author panda
 */
public class NoImageReadingViewHolderBinder extends ViewHolderBinder<DmBaseViewHolder<XianduData>, XianduData> {

    @Override
    public DmBaseViewHolder<XianduData> onCreateViewHolder(ViewGroup parentView) {
        return new NoImageViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(DmBaseViewHolder<XianduData> viewHolder, XianduData commonResource, int position) {
        viewHolder.updateData(commonResource, position);
    }


    public static class NoImageViewHolder extends DmBaseViewHolder<XianduData>{
        private TextView mTvTitle;
        private TextView mTvSubTitle;
        private XianduData mData;
        private TextView mTvDate;
        private ImageView mIvSiteIcon;

        public NoImageViewHolder(ViewGroup parentView) {
            super(LayoutInflater.from(parentView.getContext()).inflate(R.layout.dm_viewholder_xiandu_item_no_image, parentView, false));
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
        public void updateData(XianduData xianduData, int position) {
            mData = xianduData;
            boolean matched = false;
            if(mData != null){
                mTvTitle.setText(xianduData.title);
                mTvSubTitle.setText(xianduData.content);
                String displayPublishAt = mData.getDisplayPublishAt();
                mTvDate.setText(DateUtils.friendlyTime(displayPublishAt));

                if(xianduData.site != null && !TextUtils.isEmpty(xianduData.site.url)){
                    matched = true;
                    mIvSiteIcon.setVisibility(View.VISIBLE);
                    Glide.with(itemView.getContext()).load(xianduData.site.icon)
                            .apply(RequestOptions.centerCropTransform()
                                    .placeholder(R.mipmap.ic_launcher)).into(mIvSiteIcon);
                }
            }
            if(!matched){
                Glide.with(itemView.getContext()).load(R.mipmap.ic_launcher)
                        .apply(RequestOptions.centerCropTransform()
                                .placeholder(R.mipmap.ic_launcher)).into(mIvSiteIcon);
                mIvSiteIcon.setVisibility(View.GONE);
            }
        }
    }


}
