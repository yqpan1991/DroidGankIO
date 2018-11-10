package com.edus.gankio.ui.adapter.multi;

/**
 * DefaultSubTypeLinker,which just have one subType, so implements class: ViewHolderBinder = 1:1
 * @param <T>
 */
public class DefaultSubTypeLinker<T> implements SubTypeLinker<T> {
    private ViewHolderBinder mViewHolderBinder;

    public DefaultSubTypeLinker(ViewHolderBinder viewHolderBinder){
        mViewHolderBinder = viewHolderBinder;
    }

    @Override
    public String getSubType(T t) {
        return "0";
    }

    @Override
    public ViewHolderBinder onCreateViewHolderBinder(String subType, T t) {
        return mViewHolderBinder;
    }
}
