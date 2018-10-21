package com.edus.gankio.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.edus.gankio.R;

/**
 * 页面从加载中,到加载的结果(正常数据结果页,空页面,错误页面)
 */
public class LoadingAndResultContainer extends FrameLayout {

    public enum DISPLAY_STATUS {
        LOADING(0),
        COMMON_RESULT(1),
        EMPTY(2),
        ERROR(3);

        private int status;

        private DISPLAY_STATUS(int status){
            this.status = status;
        }

        public int getStatus(){
            return status;
        }

        public static DISPLAY_STATUS fromValue(int status){
            if(status == LOADING.getStatus()){
                return LOADING;
            }else if(status == COMMON_RESULT.getStatus()){
                return COMMON_RESULT;
            }else if(status == EMPTY.getStatus()){
                return EMPTY;
            }else if(status == ERROR.getStatus()){
                return ERROR;
            }else{
                return null;
            }
        }

    };

    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mContentView;

    private DISPLAY_STATUS mDisplayStatus;

    public LoadingAndResultContainer(@NonNull Context context) {
        this(context, null);
    }

    public LoadingAndResultContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingAndResultContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }


    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        if(attrs == null || context == null){
            return;
        }
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingAndResultContainer, defStyleAttr, 0);
        int loadingResId = ta.getResourceId(R.styleable.LoadingAndResultContainer_loading_id, 0);
        int contentResId = ta.getResourceId(R.styleable.LoadingAndResultContainer_content_id, 0);
        int emptyResId = ta.getResourceId(R.styleable.LoadingAndResultContainer_empty_id, 0);
        int errorResId = ta.getResourceId(R.styleable.LoadingAndResultContainer_error_id, 0);
        Integer defaultStatus = ta.getInteger(R.styleable.LoadingAndResultContainer_default_status, DISPLAY_STATUS.LOADING.getStatus());
        if(loadingResId > 0){
            mLoadingView = LayoutInflater.from(getContext()).inflate(loadingResId, this, false);
            addView(mLoadingView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        if(contentResId > 0){
            mContentView = LayoutInflater.from(getContext()).inflate(contentResId, this, false);
            addView(mContentView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        if(emptyResId > 0){
            mEmptyView = LayoutInflater.from(getContext()).inflate(emptyResId, this, false);
            addView(mEmptyView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        if(errorResId > 0){
            mErrorView = LayoutInflater.from(getContext()).inflate(errorResId, this, false);
            addView(mErrorView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        DISPLAY_STATUS displayStatus = DISPLAY_STATUS.LOADING;
        if(defaultStatus != null){
            displayStatus = DISPLAY_STATUS.fromValue(defaultStatus.intValue());
        }
        ta.recycle();
        refreshStatus(displayStatus);
    }

    private void refreshStatus(DISPLAY_STATUS displayStatus) {
        if(isSameStatus(displayStatus)){
            return;
        }
        if(mDisplayStatus == DISPLAY_STATUS.LOADING){
            showLoading();
        }else if(mDisplayStatus == DISPLAY_STATUS.COMMON_RESULT){
            showCommonResult();
        }else if(mDisplayStatus == DISPLAY_STATUS.EMPTY){
            showEmpty();
        }else if(mDisplayStatus == DISPLAY_STATUS.ERROR){
            showError();
        }
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public void setLoadingView(View loadingView) {
        if(loadingView == null){
            return;
        }
        if(loadingView.getParent() != null){
            throw new RuntimeException("setLoadingView view cannot have parent");
        }
        if(mLoadingView != null){
            removeView(mLoadingView);
        }
        this.mLoadingView = loadingView;
        addView(mLoadingView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public void setEmptyView(View emptyView) {
        if(emptyView == null){
            return;
        }
        if(emptyView.getParent() != null){
            throw new RuntimeException("setEmptyView view cannot have parent");
        }
        if(mEmptyView != null){
            removeView(mEmptyView);
        }
        this.mEmptyView = emptyView;
        addView(mEmptyView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public View getErrorView() {
        return mErrorView;
    }

    public void setErrorView(View errorView) {
        if(errorView == null){
            return;
        }
        if(errorView.getParent() != null){
            throw new RuntimeException("setEmptyView view cannot have parent");
        }
        if(mErrorView != null){
            removeView(mErrorView);
        }
        this.mErrorView = errorView;
        addView(mErrorView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public View getContentView() {
        return mContentView;
    }

    public void setContentView(View contentView) {
        if(contentView == null){
            return;
        }
        if(contentView.getParent() != null){
            throw new RuntimeException("setEmptyView view cannot have parent");
        }
        if(mContentView != null){
            removeView(mContentView);
        }
        this.mContentView = contentView;
        addView(mContentView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public DISPLAY_STATUS getDisplayStatus() {
        return mDisplayStatus;
    }

    public void showLoading(){
        if(isSameStatus(DISPLAY_STATUS.LOADING)){
            return;
        }
        mDisplayStatus = DISPLAY_STATUS.LOADING;
        setViewVisibility(mLoadingView, View.VISIBLE);
        setViewVisibility(mContentView, View.GONE);
        setViewVisibility(mEmptyView, View.GONE);
        setViewVisibility(mErrorView, View.GONE);
    }

    private boolean isSameStatus(DISPLAY_STATUS status) {
        return mDisplayStatus == status;
    }

    public void showCommonResult(){
        if(isSameStatus(DISPLAY_STATUS.COMMON_RESULT)){
            return;
        }
        mDisplayStatus = DISPLAY_STATUS.COMMON_RESULT;
        setViewVisibility(mLoadingView, View.GONE);
        setViewVisibility(mContentView, View.VISIBLE);
        setViewVisibility(mEmptyView, View.GONE);
        setViewVisibility(mErrorView, View.GONE);
    }

    public void showEmpty(){
        if(isSameStatus(DISPLAY_STATUS.EMPTY)){
            return;
        }
        mDisplayStatus = DISPLAY_STATUS.EMPTY;
        setViewVisibility(mLoadingView, View.GONE);
        setViewVisibility(mContentView, View.GONE);
        setViewVisibility(mEmptyView, View.VISIBLE);
        setViewVisibility(mErrorView, View.GONE);
    }

    public void showError(){
        if(isSameStatus(DISPLAY_STATUS.ERROR)){
            return;
        }
        mDisplayStatus = DISPLAY_STATUS.ERROR;
        setViewVisibility(mLoadingView, View.GONE);
        setViewVisibility(mContentView, View.GONE);
        setViewVisibility(mEmptyView, View.GONE);
        setViewVisibility(mErrorView, View.VISIBLE);
    }

    private void setViewVisibility(View view , int visibility){
        if(view != null){
            view.setVisibility(visibility);
        }
    }

}
