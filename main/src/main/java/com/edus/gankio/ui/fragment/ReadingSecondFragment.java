package com.edus.gankio.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apollo.edus.biz.aop.AopImpl;
import com.apollo.edus.uilibrary.widget.loadingandresult.LoadingAndResultContainer;
import com.edus.gankio.R;
import com.edus.gankio.cache.CacheManager;
import com.edus.gankio.cache.MemoryCache;
import com.edus.gankio.data.CommonResult;
import com.edus.gankio.data.XianduCategoryItem;
import com.edus.gankio.data.XianduSubCategoryItem;
import com.edus.gankio.net.ApiService;
import com.edus.gankio.net.DataCallback;
import com.edus.gankio.ui.adapter.CategorySubSelectAdapter;
import com.edus.gankio.ui.adapter.OnItemSelectListener;
import com.edus.gankio.ui.widget.LoadingErrorView;
import com.google.gson.Gson;

import java.util.List;

public class ReadingSecondFragment extends Fragment {
    private static final String TAG = ReadingSecondFragment.class.getSimpleName();
    private static final String BUNDLE_KEY_DATA = "reading_second_fragment_data";

    private LoadingAndResultContainer mLarcContainer;
    private RecyclerView mRvCategory;
    private LoadingErrorView mLoadingErrorView;

    private CategorySubSelectAdapter mAdapter;
    private int mSubSelectedCategoryPosition;

    private XianduCategoryItem mXianduCategoryItem;

    public static ReadingSecondFragment getInstance(XianduCategoryItem xianduCategoryItem){
        ReadingSecondFragment fragment = new ReadingSecondFragment();
        if(xianduCategoryItem != null){
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_KEY_DATA, xianduCategoryItem);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            mXianduCategoryItem = (XianduCategoryItem) arguments.getSerializable(BUNDLE_KEY_DATA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_reading_second_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLarcContainer = view.findViewById(R.id.larc_container);
        mRvCategory = view.findViewById(R.id.rv_category);
        mLoadingErrorView = (LoadingErrorView) mLarcContainer.getErrorView();
        mLoadingErrorView.setErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleReloadCategory();
            }
        });
    }

    private void handleReloadCategory() {
        loadData(mXianduCategoryItem);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new CategorySubSelectAdapter();
        mAdapter.setSelectListener(new OnItemSelectListener() {
            @Override
            public void onItemSelected(int selectedPosition) {
                mSubSelectedCategoryPosition = selectedPosition;
                Log.e(TAG, "onSubItemSelected pos:"+selectedPosition);
                handleLoadContent();
            }
        });
        mRvCategory.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvCategory.setAdapter(mAdapter);
        loadData(mXianduCategoryItem);
    }

    private void handleLoadContent() {
        XianduSubCategoryItem item = mAdapter.getItem(mSubSelectedCategoryPosition);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_third_container, ReadingPageContentFragment.getInstance(item)).commitAllowingStateLoss();
    }

    private void loadData(final XianduCategoryItem item){
        if(item == null){
            mLarcContainer.setVisibility(View.GONE);
            return;
        }
        mLarcContainer.setVisibility(View.VISIBLE);
        MemoryCache<XianduSubCategoryItem> subCategoryCache = CacheManager.getInstance().getSubCategoryCache(item.enName);
        if(subCategoryCache != null){
            List<XianduSubCategoryItem> dataList = subCategoryCache.getDataList();
            if(dataList == null || dataList.isEmpty()){
                mLarcContainer.showEmpty();
            }else{
                mAdapter.setCategoryList(dataList);
                if(mAdapter.getSelectedPos() != mSubSelectedCategoryPosition){
                    mAdapter.setSelectedPosition(mSubSelectedCategoryPosition);
                }
                mLarcContainer.showCommonResult();
                handleLoadContent();
            }
        }else{
            ApiService.getInstance().getXianduSubCategoryList(item.enName, AopImpl.getInstance().makeFragmentAop(this, new DataCallback<CommonResult<List<XianduSubCategoryItem>>>() {
                @Override
                public void onReceived(CommonResult<List<XianduSubCategoryItem>> data) {
                    Log.e(TAG, "isNotNull:"+(data.results != null));
                    Log.e(TAG, "isEmpty:"+(data.results.isEmpty()));
                    if(data != null){
                        if(data.error){
                            mLarcContainer.showError();
                        }else{
                            MemoryCache<XianduSubCategoryItem> memoryCache = new MemoryCache<>(Integer.MAX_VALUE);
                            memoryCache.setDataList(data.results);
                            CacheManager.getInstance().putSubCategoryCache(item.enName, memoryCache);
                            if(data.results == null || data.results.isEmpty()){
                                mLarcContainer.showEmpty();
                            }else{
                                mAdapter.setCategoryList(data.results);
                                if(mAdapter.getSelectedPos() != mSubSelectedCategoryPosition){
                                    mAdapter.setSelectedPosition(mSubSelectedCategoryPosition);
                                }
                                mLarcContainer.showCommonResult();
                                handleLoadContent();
                            }
                        }
                    }else{
                        mLarcContainer.showEmpty();
                    }
                }

                @Override
                public void onException(Throwable throwable) {
                    mLarcContainer.showError();
                }
            }));
        }

    }


}
