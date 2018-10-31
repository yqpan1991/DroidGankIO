package com.edus.gankio.ui.fragment;

import android.os.Bundle;

import com.edus.gankio.data.CommonResult;
import com.edus.gankio.net.ApiService;
import com.edus.gankio.net.DataCallback;
import com.edus.gankio.ui.adapter.BaseRvAdapter;
import com.edus.gankio.ui.adapter.HomePageHomeAdapter;

import java.util.List;

public class HomePageHomeFragment extends HomeBaseFragment<String> {

    public static HomePageHomeFragment getInstance(Bundle bundle){
        HomePageHomeFragment homePageHomeFragment = new HomePageHomeFragment();
        homePageHomeFragment.setArguments(bundle);
        return homePageHomeFragment;
    }


    @Override
    protected BaseRvAdapter onCreateAdapter() {
        return new HomePageHomeAdapter();
    }

    @Override
    protected void handleRpcFirstPage() {
        getLarcContent().showLoading();
        ApiService.getInstance().getGanHuoDate(new DataCallback<CommonResult<List<String>>>() {
            @Override
            public void onReceived(CommonResult<List<String>> data) {
                if(data != null && !data.error && data.results != null && !data.results.isEmpty()){
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
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
