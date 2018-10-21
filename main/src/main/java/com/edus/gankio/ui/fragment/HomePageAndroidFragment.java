package com.edus.gankio.ui.fragment;

import android.os.Bundle;

import com.edus.gankio.data.CommonResource;
import com.edus.gankio.data.CommonResult;
import com.edus.gankio.net.ApiService;
import com.edus.gankio.net.DataCallback;
import com.edus.gankio.ui.adapter.BaseRvAdapter;
import com.edus.gankio.ui.adapter.HomePageAndroidAdapter;

import java.util.List;

public class HomePageAndroidFragment extends HomeBaseFragment<CommonResource> {
    private final int PAGE_SIZE = 20;
    private int mPageIndex = 1;

    public static HomePageAndroidFragment getInstance(Bundle bundle){
        HomePageAndroidFragment homePageHomeFragment = new HomePageAndroidFragment();
        homePageHomeFragment.setArguments(bundle);
        return homePageHomeFragment;
    }


    @Override
    protected BaseRvAdapter onCreateAdapter() {
        return new HomePageAndroidAdapter();
    }

    @Override
    protected void handleRpc() {
        getLarcContent().showLoading();
        ApiService.getInstance().getAndroidResource(new DataCallback<CommonResult<List<CommonResource>>>() {
            @Override
            public void onReceived(CommonResult<List<CommonResource>> data) {
                if(data != null && !data.error){
                    getAdapter().setDataList(data.results);
                    getLarcContent().showCommonResult();
                }else{
                    getLarcContent().showEmpty();
                }

            }

            @Override
            public void onException(Throwable throwable) {
                getLarcContent().showError();

            }
        }, PAGE_SIZE, mPageIndex);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
