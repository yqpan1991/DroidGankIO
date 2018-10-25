package com.edus.gankio.ui.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.edus.gankio.R;

public class LoadingViewDefaultBehavior implements LoadingAndResultContainer.Behavior {
    @Override
    public View onAttachedView(ViewGroup parentView) {
        View currentView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.layout_default_loading, parentView, false);
        parentView.addView(currentView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return currentView;
    }
}
