package com.edus.gankio.ui.adapter.multi;

public interface SubTypeLinker<T> {
    int getSubType(T t);
    ViewHolderBinder onCreateViewHolderBinder(T t);
}
