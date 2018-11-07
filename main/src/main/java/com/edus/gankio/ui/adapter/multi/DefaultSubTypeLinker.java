package com.edus.gankio.ui.adapter.multi;

public class DefaultSubTypeLinker<T> implements SubTypeLinker<T> {
    private ViewHolderBinder mViewHolderBinder;

    public DefaultSubTypeLinker(ViewHolderBinder viewHolderBinder){
        mViewHolderBinder = viewHolderBinder;
    }

    @Override
    public int getSubType(T t) {
        return 0;
    }

    @Override
    public ViewHolderBinder onCreateViewHolderBinder(T t) {
        return mViewHolderBinder;
    }
}
