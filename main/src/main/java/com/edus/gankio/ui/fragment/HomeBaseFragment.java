package com.edus.gankio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edus.gankio.R;
import com.edus.gankio.ui.adapter.BaseRvAdapter;
import com.edus.gankio.ui.adapter.HomePageHomeAdapter;
import com.edus.gankio.ui.widget.LoadingAndResultContainer;


public abstract class HomeBaseFragment<DataType> extends Fragment {
    private RecyclerView mRvContent;
    private BaseRvAdapter mAdapter;
    private LoadingAndResultContainer mLarcContent;
    private TextView mTvRetry;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_home_page_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
    }

    private void initView(View rootView) {
        mLarcContent = (LoadingAndResultContainer) rootView.findViewById(R.id.larc_container);
        mRvContent = (RecyclerView) mLarcContent.getContentView().findViewById(R.id.rv_content);
        mTvRetry = (TextView) mLarcContent.getErrorView().findViewById(R.id.tv_retry);
    }


    private void initListener() {
        mTvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRpc();
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLarcContent.showLoading();
        initData();
    }

    protected abstract BaseRvAdapter onCreateAdapter();

    protected BaseRvAdapter getAdapter(){
        if(mAdapter == null){
            mAdapter = onCreateAdapter();
        }
        return mAdapter;
    }

    private void initData() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = onCreateAdapter();
        mRvContent.setAdapter(mAdapter);
        handleRpc();
    }

    public RecyclerView getRvContent() {
        return mRvContent;
    }

    public LoadingAndResultContainer getLarcContent() {
        return mLarcContent;
    }

    /**
     * 加载第一个页
     */
    protected abstract void handleRpc();
}
