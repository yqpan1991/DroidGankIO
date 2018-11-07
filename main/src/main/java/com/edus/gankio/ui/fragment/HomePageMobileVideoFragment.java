package com.edus.gankio.ui.fragment;

import android.os.Bundle;

import com.edus.gankio.data.CommonResource;
import com.edus.gankio.data.CommonResult;
import com.edus.gankio.net.ApiService;
import com.edus.gankio.net.DataCallback;

import java.util.List;

public class HomePageMobileVideoFragment extends HomePageMobileBaseFragment {

    public static HomePageMobileVideoFragment getInstance(Bundle bundle){
        HomePageMobileVideoFragment androidFragment = new HomePageMobileVideoFragment();
        androidFragment.setArguments(bundle);
        return androidFragment;
    }

    @Override
    protected void loadResource(DataCallback<CommonResult<List<CommonResource>>> callback, int pageSize, int pageIndex) {
        ApiService.getInstance().getVideoResource(callback, pageSize, pageIndex);
    }
}
