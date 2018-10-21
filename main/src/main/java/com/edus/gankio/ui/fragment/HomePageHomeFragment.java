package com.edus.gankio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edus.gankio.R;
import com.edus.gankio.data.CommonResult;
import com.edus.gankio.net.ApiService;
import com.edus.gankio.net.DataCallback;
import com.edus.gankio.ui.adapter.HomePageHomeAdapter;

import java.util.List;

public class HomePageHomeFragment extends Fragment {
    private RecyclerView mRvContent;
    private HomePageHomeAdapter mAdapter;

    public static HomePageHomeFragment getInstance(Bundle bundle){
        HomePageHomeFragment homePageHomeFragment = new HomePageHomeFragment();
        homePageHomeFragment.setArguments(bundle);
        return homePageHomeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_home_page_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvContent = (RecyclerView) view.findViewById(R.id.rv_content);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }



    private void initData() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new HomePageHomeAdapter();
        mRvContent.setAdapter(mAdapter);

        ApiService.getInstance().getGanHuoDate(new DataCallback<CommonResult<List<String>>>() {
            @Override
            public void onReceived(CommonResult<List<String>> data) {
                if(data != null && !data.error){
                    mAdapter.setDataList(data.results);
                }
            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
