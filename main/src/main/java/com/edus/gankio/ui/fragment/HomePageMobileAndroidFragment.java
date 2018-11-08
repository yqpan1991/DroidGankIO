package com.edus.gankio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.edus.gankio.data.CommonResource;
import com.edus.gankio.data.CommonResult;
import com.edus.gankio.net.ApiService;
import com.edus.gankio.net.DataCallback;
import com.edus.gankio.ui.adapter.holder.SingleImageViewHolderBinder;
import com.edus.gankio.ui.adapter.holder.TripleImageViewHolderBinder;
import com.edus.gankio.ui.adapter.multi.SubTypeLinker;
import com.edus.gankio.ui.adapter.multi.ViewHolderBinder;

import java.util.List;

public class HomePageMobileAndroidFragment extends HomePageMobileBaseFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerViewHolderBinder();
    }

    public static HomePageMobileAndroidFragment getInstance(Bundle bundle){
        HomePageMobileAndroidFragment androidFragment = new HomePageMobileAndroidFragment();
        androidFragment.setArguments(bundle);
        return androidFragment;
    }

    @Override
    protected void loadResource(DataCallback<CommonResult<List<CommonResource>>> callback, int pageSize, int pageIndex) {
        ApiService.getInstance().getAndroidResource(callback, pageSize, pageIndex);
    }


    private void registerViewHolderBinder() {
        getAdapter().registerMultiType(CommonResource.class, new SubTypeLinker<CommonResource>() {
            private final int SUB_TYPE_TRIPLE_IMAGE = 2;
            private final int SUB_TYPE_SINGLE_IAMGE = 1;
            @Override
            public int getSubType(CommonResource commonResource) {
                if(commonResource == null){
                    throw new RuntimeException("commonResouce cannot be null");
                }
                if(commonResource.images != null && commonResource.images.size() >= 3){
                    return SUB_TYPE_TRIPLE_IMAGE;
                }else{
                    return SUB_TYPE_SINGLE_IAMGE;
                }
            }

            @Override
            public ViewHolderBinder onCreateViewHolderBinder(int subType, CommonResource commonResource) {
                if(subType == SUB_TYPE_TRIPLE_IMAGE){
                    return new TripleImageViewHolderBinder();
                }else{
                    return new SingleImageViewHolderBinder();
                }
            }
        });
    }

}
