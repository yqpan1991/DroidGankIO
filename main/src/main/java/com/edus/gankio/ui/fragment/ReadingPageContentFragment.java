package com.edus.gankio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apollo.edus.biz.aop.AopImpl;
import com.apollo.edus.uilibrary.widget.loadingandresult.LoadingAndResultContainer;
import com.edus.gankio.R;
import com.edus.gankio.cache.CacheManager;
import com.edus.gankio.cache.MemoryCache;
import com.edus.gankio.data.CommonResult;
import com.edus.gankio.data.XianduData;
import com.edus.gankio.data.XianduSubCategoryItem;
import com.edus.gankio.net.ApiService;
import com.edus.gankio.net.DataCallback;
import com.edus.gankio.ui.adapter.HomePageBaseAdapter;
import com.edus.gankio.ui.adapter.holder.NoImageReadingViewHolderBinder;
import com.edus.gankio.ui.adapter.holder.SingleImageReadingViewHolderBinder;
import com.edus.gankio.ui.adapter.multi.SubTypeLinker;
import com.edus.gankio.ui.adapter.multi.ViewHolderBinder;
import com.edus.gankio.ui.widget.LoadingErrorView;
import com.edus.gankio.ui.widget.recyclerview.DmRecyclerViewWrapper;
import com.edus.gankio.ui.widget.recyclerview.decoration.LinearItemDividerDecoration;

import java.util.ArrayList;
import java.util.List;

public class ReadingPageContentFragment extends Fragment {
    private static final String BUNDLE_KEY_DATA = "reading_page_content_data";
    private final int PAGE_SIZE = 20;
    private int mNextPageIndex = 1;

    private DmRecyclerViewWrapper mRvContent;
    private HomePageBaseAdapter<XianduData> mAdapter;
    private LoadingAndResultContainer mLarcContent;
    private LoadingErrorView mLoadingErrorView;
    private XianduSubCategoryItem mSubCategoryItem;

    public static ReadingPageContentFragment getInstance(XianduSubCategoryItem item){
        ReadingPageContentFragment fragment = new ReadingPageContentFragment();
        if(item != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_KEY_DATA, item);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            mSubCategoryItem = (XianduSubCategoryItem) arguments.getSerializable(BUNDLE_KEY_DATA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_reading_page_content, container, false);
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
        getAdapter().registerMultiType(XianduData.class, new SubTypeLinker<XianduData>() {
            private final String SUB_TYPE_SINGLE_IMAGE = "1";
            private final String SUB_TYPE_NO_IMAGE = "0";
            @Override
            public String getSubType(XianduData data) {
                if(data == null){
                    throw new RuntimeException("XianduData cannot be null");
                }
                if(TextUtils.isEmpty(data.cover) || "none".equalsIgnoreCase(data.cover)){
                    return SUB_TYPE_NO_IMAGE;
                }else{
                    return SUB_TYPE_SINGLE_IMAGE;
                }
            }

            @Override
            public ViewHolderBinder onCreateViewHolderBinder(String subType, XianduData data) {
                if(SUB_TYPE_NO_IMAGE.equals(subType)){
                    return new NoImageReadingViewHolderBinder();
                }else{
                    return new SingleImageReadingViewHolderBinder();
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
        loadResource( new DataCallback<CommonResult<List<XianduData>>>() {
            @Override
            public void onReceived(CommonResult<List<XianduData>> data) {
                MemoryCache<XianduData> xianDuCache = CacheManager.getInstance().getXianDuCache(mSubCategoryItem.id +  mSubCategoryItem.subCategoryId);;
                if(xianDuCache == null){
                    throw new RuntimeException("cache cannot be null");
                }
                mNextPageIndex++;
                xianDuCache.setPageIndex(mNextPageIndex);
                List<XianduData> dataList = new ArrayList<>();
                xianDuCache.setDataList(dataList);
                boolean enableLoadMore = false;
                if(data != null && !data.error){
                    if(data.results != null){
                        getAdapter().addDataList(data.results);
                    }
                    List<XianduData> adapterDataList = getAdapter().getDataList();
                    if(adapterDataList != null){
                        dataList.addAll(adapterDataList);
                    }

                    if(data.results == null || data.results.size() < PAGE_SIZE){
                        enableLoadMore = false;
                        mRvContent.enableLoadMore(enableLoadMore);
                    }else{
                        enableLoadMore = true;
                        mRvContent.enableLoadMore(enableLoadMore);
                    }

                }else{
                    enableLoadMore = false;
                    mRvContent.enableLoadMore(enableLoadMore);
                }

                xianDuCache.setHasMore(enableLoadMore);
            }

            @Override
            public void onException(Throwable throwable) {
                Toast.makeText(getActivity(), throwable.toString(), Toast.LENGTH_SHORT).show();

            }
        }, PAGE_SIZE, mNextPageIndex);
    }

    protected void loadResource(DataCallback<CommonResult<List<XianduData>>> callback, int page_size, int mNextPageIndex){
        if(mSubCategoryItem != null){
            ApiService.getInstance().getXianduListResource(mSubCategoryItem.subCategoryId, page_size, mNextPageIndex, callback);
        }else{
            mLarcContent.showEmpty();
        }
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            handleRpcFirstPage(true);
        }
    };

    protected HomePageBaseAdapter<XianduData> getAdapter(){
        return mAdapter;
    }

    protected HomePageBaseAdapter<XianduData> onCreateAdapter() {
        return new HomePageBaseAdapter<>(getContext());
    }

    protected void handleRpcFirstPage(final boolean userRefresh) {
        mNextPageIndex = 1;
        if(userRefresh){
        }else{
            getLarcContent().showLoading();
        }
        mRvContent.enableRefresh(true);
       /* loadResource(AopImpl.getInstance().makeFragmentAop(this, new DataCallback<CommonResult<List<XianduData>>>() {
            @Override
            public void onReceived(CommonResult<List<XianduData>> data) {
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
        }), PAGE_SIZE, mNextPageIndex);*/
       //1. 获取到缓存中的数据,如果没有或者数据为null时,清除数据,直接请求
        //2. 数据存在时,更改当前的page信息
        if(!userRefresh && mSubCategoryItem != null){
            String key = mSubCategoryItem.id + mSubCategoryItem.subCategoryId;
            MemoryCache<XianduData> xianDuCache = CacheManager.getInstance().getXianDuCache(key);
            if(xianDuCache != null && xianDuCache.getPageIndex() >= mNextPageIndex){
                List<XianduData> dataList = xianDuCache.getDataList();
                if(dataList != null && !dataList.isEmpty()){
                    //直接显示结果
                    getAdapter().setDataList(dataList);
                    if(!userRefresh){
                        getLarcContent().showCommonResult();
                    }
                    mNextPageIndex = xianDuCache.getPageIndex();
                    mRvContent.enableLoadMore(xianDuCache.isHasMore());
                    return;
                }else{
                    CacheManager.getInstance().putXianDuCache(key, null);
                }
            }
        }
        loadResource(new DataCallback<CommonResult<List<XianduData>>>() {
            @Override
            public void onReceived(CommonResult<List<XianduData>> data) {
                if(userRefresh){
                    mRvContent.setRefreshing(false);
                }
                mNextPageIndex ++;
                MemoryCache<XianduData> memoryCache = new MemoryCache<>(PAGE_SIZE);
                ArrayList<XianduData> dataList = new ArrayList<>();
                memoryCache.setPageIndex(mNextPageIndex);
                memoryCache.setDataList(dataList);
                if(data != null && !data.error){
                    getAdapter().setDataList(data.results);
                    if(!userRefresh){
                        getLarcContent().showCommonResult();
                    }
                    List<XianduData> adapterDataList = getAdapter().getDataList();
                    if(adapterDataList != null){
                        dataList.addAll(adapterDataList);
                    }
                    if(data.results == null || data.results.size() < PAGE_SIZE){
                        mRvContent.enableLoadMore(false);
                        memoryCache.setHasMore(false);
                    }else{
                        mRvContent.enableLoadMore(true);
                        memoryCache.setHasMore(true);
                    }
                }else{
                    memoryCache.setHasMore(false);
                    mRvContent.enableLoadMore(false);
                    if(!userRefresh){
                        getLarcContent().showEmpty();
                    }
                }
                CacheManager.getInstance().putXianDuCache(mSubCategoryItem.id + mSubCategoryItem.subCategoryId, memoryCache);

            }

            @Override
            public void onException(Throwable throwable) {
                if(throwable != null){
                    throwable.printStackTrace();
                }
                if(userRefresh){
                    mRvContent.setRefreshing(false);
                }else{
                    getLarcContent().showError();
                }

            }
        }, PAGE_SIZE, mNextPageIndex);
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
