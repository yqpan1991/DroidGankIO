package com.edus.gankio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apollo.edus.biz.aop.AopImpl;
import com.apollo.edus.uilibrary.widget.loadingandresult.LoadingAndResultContainer;
import com.edus.gankio.R;
import com.edus.gankio.data.CommonResource;
import com.edus.gankio.data.CommonResult;
import com.edus.gankio.net.DataCallback;
import com.edus.gankio.ui.adapter.HomePageBaseAdapter;
import com.edus.gankio.ui.adapter.holder.NoImageViewHolderBinder;
import com.edus.gankio.ui.adapter.holder.SingleImageViewHolderBinder;
import com.edus.gankio.ui.adapter.holder.TripleImageViewHolderBinder;
import com.edus.gankio.ui.adapter.multi.SubTypeLinker;
import com.edus.gankio.ui.adapter.multi.ViewHolderBinder;
import com.edus.gankio.ui.widget.LoadingErrorView;
import com.edus.gankio.ui.widget.recyclerview.DmRecyclerViewWrapper;
import com.edus.gankio.ui.widget.recyclerview.decoration.LinearItemDividerDecoration;

import java.util.List;

public abstract class HomePageMobileBaseFragment extends Fragment {
    private final int PAGE_SIZE = 20;
    private int mNextPageIndex = 1;

    private DmRecyclerViewWrapper mRvContent;
    private HomePageBaseAdapter<CommonResource> mAdapter;
    private LoadingAndResultContainer mLarcContent;
    private LoadingErrorView mLoadingErrorView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_home_page_android, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
    }

    private void initView(View rootView) {
        mLarcContent = rootView.findViewById(R.id.larc_container);
        mRvContent =  mLarcContent.getContentView().findViewById(R.id.rv_content);
        mLoadingErrorView = (LoadingErrorView) mLarcContent.getErrorView();
    }

    private void initListener() {
        mLoadingErrorView.setErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRpcFirstPage(false);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLarcContent.showLoading();
        initData();
    }

    private void initData() {
        onInstallRecyclerView(mRvContent);
        mRvContent.setOnLoadMoreListener(mOnLoadMoreListener);
        mRvContent.setOnRefreshListener(mOnRefreshListener);
        mAdapter = onCreateAdapter();
        mRvContent.setAdapter(mAdapter);

        onRegisterMultiType();

        handleRpcFirstPage(false);
    }

    protected void onInstallRecyclerView(DmRecyclerViewWrapper recyclerViewWrapper) {
        if(recyclerViewWrapper == null){
            return;
        }
        recyclerViewWrapper.addItemDecoration(new LinearItemDividerDecoration(getActivity(), LinearItemDividerDecoration.VERTICAL_LIST));
        recyclerViewWrapper.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    protected void onRegisterMultiType() {
        getAdapter().registerMultiType(CommonResource.class, new SubTypeLinker<CommonResource>() {
            private final String SUB_TYPE_TRIPLE_IMAGE = "2";
            private final String SUB_TYPE_SINGLE_IMAGE = "1";
            private final String SUB_TYPE_NO_IMAGE = "3";
            @Override
            public String getSubType(CommonResource commonResource) {
                if(commonResource == null){
                    throw new RuntimeException("commonResouce cannot be null");
                }
                if(commonResource.images != null && commonResource.images.size() >= 3){
                    return SUB_TYPE_TRIPLE_IMAGE;
                }else if(commonResource.images == null || commonResource.images.isEmpty()){
                    return SUB_TYPE_NO_IMAGE;
                }else{
                    return SUB_TYPE_SINGLE_IMAGE;
                }
            }

            @Override
            public ViewHolderBinder onCreateViewHolderBinder(String subType, CommonResource commonResource) {
                if(SUB_TYPE_TRIPLE_IMAGE.equals(subType)){
                    return new TripleImageViewHolderBinder();
                }else if(SUB_TYPE_NO_IMAGE.equals(subType)){
                    return new NoImageViewHolderBinder();
                }else{
                    return new SingleImageViewHolderBinder();
                }
            }
        });
    }

    private DmRecyclerViewWrapper.OnLoadMoreListener mOnLoadMoreListener = new DmRecyclerViewWrapper.OnLoadMoreListener() {
        @Override
        public void loadMore(int itemsCount, int maxLastVisiblePosition) {
            handleLoadMore();
        }
    };

    private void handleLoadMore() {
        loadResource(AopImpl.getInstance().makeFragmentAop(this, new DataCallback<CommonResult<List<CommonResource>>>() {
            @Override
            public void onReceived(CommonResult<List<CommonResource>> data) {
                if(data != null && !data.error){
                    if(data.results == null || data.results.size() < PAGE_SIZE){
                        mRvContent.enableLoadMore(false);
                    }else{
                        mNextPageIndex++;
                        mRvContent.enableLoadMore(true);
                        getAdapter().addDataList(data.results);
                    }
                }else{
                    mRvContent.enableLoadMore(false);
                }
            }

            @Override
            public void onException(Throwable throwable) {
                Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();

            }
        }), PAGE_SIZE, mNextPageIndex);
    }

    protected abstract void loadResource(DataCallback<CommonResult<List<CommonResource>>> callback, int page_size, int mNextPageIndex);

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            handleRpcFirstPage(true);
        }
    };

    protected HomePageBaseAdapter<CommonResource> getAdapter(){
        return mAdapter;
    }

    protected HomePageBaseAdapter<CommonResource> onCreateAdapter() {
        return new HomePageBaseAdapter<>(getContext());
    }

    protected void handleRpcFirstPage(final boolean userRefresh) {
        mNextPageIndex = 1;
        if(userRefresh){
        }else{
            getLarcContent().showLoading();
        }
        mRvContent.enableRefresh(true);
        loadResource(AopImpl.getInstance().makeFragmentAop(this, new DataCallback<CommonResult<List<CommonResource>>>() {
            @Override
            public void onReceived(CommonResult<List<CommonResource>> data) {
                if(userRefresh){
                    mRvContent.setRefreshing(false);
                }
                if(data != null && !data.error){
                    getAdapter().setDataList(data.results);
                    if(!userRefresh){
                        getLarcContent().showCommonResult();
                    }
                    if(data.results == null || data.results.size() < PAGE_SIZE){
                        mRvContent.enableLoadMore(false);
                    }else{
                        mRvContent.enableLoadMore(true);
                        mNextPageIndex ++;
                    }
                }else{
                    mRvContent.enableLoadMore(false);
                    if(!userRefresh){
                        getLarcContent().showEmpty();
                    }

                }

            }

            @Override
            public void onException(Throwable throwable) {
                if(userRefresh){
                    mRvContent.setRefreshing(false);
                }else{
                    getLarcContent().showError();
                }

            }
        }), PAGE_SIZE, mNextPageIndex);
    }


    public DmRecyclerViewWrapper getRvContent() {
        return mRvContent;
    }

    public LoadingAndResultContainer getLarcContent() {
        return mLarcContent;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
