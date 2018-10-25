package com.edus.gankio.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by panda on 2018/10/19.
 */

public class SampleFragment extends Fragment {
    public static final String BUNDLE_EXTRA_KEY_TITLE = "sf_bd_extra_key_title";
    private String mTitle;

    public static SampleFragment getInstance(Bundle args){
        SampleFragment sampleFragment = new SampleFragment();
        sampleFragment.setArguments(args);
        return sampleFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments != null){
            mTitle = arguments.getString(BUNDLE_EXTRA_KEY_TITLE, "no_title");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(inflater.getContext());
        return textView;
//        return super.onAttachedView(inflater, container, savedInstanceState);
    }


}
