package com.edus.gankio.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.edus.gankio.data.CommonResource;
import com.edus.gankio.data.CommonResult;
import com.edus.gankio.net.ApiService;
import com.edus.gankio.net.DataCallback;
import com.edus.gankio.ui.adapter.holder.BonusImageViewHolderBinder;
import com.edus.gankio.ui.adapter.multi.SubTypeLinker;
import com.edus.gankio.ui.adapter.multi.ViewHolderBinder;
import com.edus.gankio.ui.widget.recyclerview.DmRecyclerViewWrapper;

import java.util.List;

public class HomePageBonusFragment extends HomePageMobileBaseFragment {

    public static HomePageBonusFragment getInstance(Bundle bundle){
        HomePageBonusFragment androidFragment = new HomePageBonusFragment();
        androidFragment.setArguments(bundle);
        return androidFragment;
    }

    @Override
    protected void onInstallRecyclerView(DmRecyclerViewWrapper recyclerViewWrapper) {
        if(recyclerViewWrapper == null){
            return;
        }
//        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewWrapper.setLayoutManager(layoutManager);
    }

    @Override
    protected void loadResource(DataCallback<CommonResult<List<CommonResource>>> callback, int pageSize, int pageIndex) {
        ApiService.getInstance().getBonusResource(callback, pageSize, pageIndex);
    }

    @Override
    protected void onRegisterMultiType() {
        //单独注册viewType
        getAdapter().registerType(CommonResource.class, new BonusImageViewHolderBinder());
    }
}
