package com.edus.gankio.ui.adapter.multi;

public interface SubTypeLinker<T> {
    /**
     * get subType by object, different subType will generate different itemViewType
     * @param t object
     * @return itemViewType's value don't have any limitation
     */
    String getSubType(T t);

    /**
     * build ViewHolderBinder
     * @param subType
     * @param t
     * @return
     */
    ViewHolderBinder onCreateViewHolderBinder(String subType, T t);
}
