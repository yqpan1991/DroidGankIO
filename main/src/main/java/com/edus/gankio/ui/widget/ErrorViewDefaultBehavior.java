package com.edus.gankio.ui.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.edus.gankio.R;

public class ErrorViewDefaultBehavior implements LoadingAndResultContainer.Behavior {
    @Override
    public View onAttachedView(ViewGroup parentView) {
        View currentView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.layout_default_error, parentView, false);
        parentView.addView(currentView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return currentView;
    }
}
