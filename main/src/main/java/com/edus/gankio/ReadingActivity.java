package com.edus.gankio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.apollo.edus.biz.aop.AopImpl;
import com.apollo.edus.uilibrary.widget.loadingandresult.LoadingAndResultContainer;
import com.edus.gankio.cache.CacheManager;
import com.edus.gankio.data.CommonResult;
import com.edus.gankio.data.XianduCategoryItem;
import com.edus.gankio.net.ApiService;
import com.edus.gankio.net.DataCallback;
import com.edus.gankio.ui.adapter.CategorySelectAdapter;
import com.edus.gankio.ui.adapter.OnItemSelectListener;
import com.edus.gankio.ui.fragment.ReadingSecondFragment;
import com.edus.gankio.ui.widget.LoadingErrorView;

import java.util.List;

public class ReadingActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private LoadingAndResultContainer mLarcContainer;
    private RecyclerView mRvCategory;
    private LoadingErrorView mLoadingErrorView;

    private CategorySelectAdapter mCategorySelectAdapter;

    private int mSelectedCategoryPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        initView();
        initListener();
        initData();
    }

    private void initListener() {
        mLoadingErrorView.setErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleReloadCategory();
            }
        });
    }

    private void handleReloadCategory() {
        handleLoadCategory();
    }

    private void initView() {
        mLarcContainer = findViewById(R.id.larc_container);
        mRvCategory = findViewById(R.id.rv_category);
        mLoadingErrorView = (LoadingErrorView) mLarcContainer.getErrorView();
    }


    private void initData() {
        mCategorySelectAdapter = new CategorySelectAdapter();
        mCategorySelectAdapter.setSelectListener(new OnItemSelectListener() {
            @Override
            public void onItemSelected(int selectedPosition) {
                mSelectedCategoryPosition = selectedPosition;
                Log.e(TAG, "onItemSelected pos:"+selectedPosition);
                handleLoadSubCategory(selectedPosition);
            }
        });
        mRvCategory.setAdapter(mCategorySelectAdapter);
        mRvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        CacheManager.getInstance();
        handleLoadCategory();
    }

    private void handleLoadCategory() {
        mLarcContainer.showLoading();
        ApiService.getInstance().getXianduCategories(AopImpl.getInstance().makeActivityAop(this, new DataCallback<CommonResult<List<XianduCategoryItem>>>() {
            @Override
            public void onReceived(CommonResult<List<XianduCategoryItem>> data) {
                if(data != null && !data.error && data.results != null && !data.results.isEmpty()){
                    showCategory(data.results);
                }else if(data.error){
                    mLarcContainer.showError();
                }else{//empty
                    mLarcContainer.showEmpty();
                }
            }

            @Override
            public void onException(Throwable throwable) {
                mLarcContainer.showError();
            }
        }));
    }

    private void showCategory(List<XianduCategoryItem> dataList) {
        if(dataList == null || dataList.isEmpty()){
            mLarcContainer.showEmpty();
        }else{
            mLarcContainer.showCommonResult();
        }
        mCategorySelectAdapter.setCategoryList(dataList);
        if(mCategorySelectAdapter.getSelectedPos() != mSelectedCategoryPosition){
            mCategorySelectAdapter.setSelectedPosition(mSelectedCategoryPosition);
            handleLoadSubCategory(mSelectedCategoryPosition);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheManager.getInstance().clearAll();
    }

    private void handleLoadSubCategory(int selectedPosition) {
        final XianduCategoryItem item = mCategorySelectAdapter.getItem(selectedPosition);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_second_container, ReadingSecondFragment.getInstance(item)).commitAllowingStateLoss();
    }



}
