package com.edus.gankio.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.edus.gankio.R;

public class LoadingErrorView extends FrameLayout {
    private TextView mTvText;
    private TextView mTvRetry;

    public LoadingErrorView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingErrorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_default_error, this, false);
        addView(rootView, layoutParams);
        mTvText = rootView.findViewById(R.id.tv_error);
        mTvRetry = rootView.findViewById(R.id.tv_retry);
    }

    public void setErrorText(String text){
        mTvText.setText(text);
    }

    public void setErrorClickListener(OnClickListener errorClickListener){
        mTvRetry.setOnClickListener(errorClickListener);
    }
}
