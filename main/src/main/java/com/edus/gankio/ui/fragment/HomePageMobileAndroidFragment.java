package com.edus.gankio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.edus.gankio.data.CommonResource;
import com.edus.gankio.data.CommonResult;
import com.edus.gankio.net.ApiService;
import com.edus.gankio.net.DataCallback;
import com.edus.gankio.ui.adapter.holder.SingleImageViewHolderBinder;
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
        getAdapter().registerType(CommonResource.class, new SingleImageViewHolderBinder());
    }

}
