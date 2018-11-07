package com.edus.gankio.ui.adapter.multi;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.HashMap;

public class TypePoolImpl implements TypePool {
    private int mStartIndex = 0;

    private static class BindedInfo{
        int itemViewType;
        ViewHolderBinder viewHolderBinder;
        public BindedInfo(int itemViewType, ViewHolderBinder viewHolderBinder){
            this.itemViewType = itemViewType;
            this.viewHolderBinder = viewHolderBinder;
        }
    }

    private static class ClzBindInfo<T> {
        HashMap<Integer, BindedInfo> subTypeBindedMap;
        SubTypeLinker<T> subTypeLinker;
        public ClzBindInfo(SubTypeLinker<T> subTypeLinker){
            this.subTypeLinker = subTypeLinker;
            subTypeBindedMap = new HashMap<>();
        }
        public BindedInfo getBindedInfoByKey(int key){
            return subTypeBindedMap.get(key);
        }
        public void putBindedInfo(int subType, BindedInfo bindedInfo){
            subTypeBindedMap.put(subType, bindedInfo);
        }
    }

    private HashMap<Class, ClzBindInfo> mClzBindInfoHashMap;
    private HashMap<Integer, ViewHolderBinder> mItemViewTypeBinderMap;

    public TypePoolImpl(int startIndex){
        mClzBindInfoHashMap = new HashMap<>();
        mItemViewTypeBinderMap = new HashMap<>();
        if(startIndex < 0 ){
            throw new RuntimeException("start index cannot be null");
        }
        mStartIndex = startIndex;
    }

    private BindedInfo getBindedInfo(Object t){
        if(t == null){
            throw new RuntimeException("param t cannot be null");
        }
        if(!mClzBindInfoHashMap.containsKey(t.getClass())){
            throw new RuntimeException(t.getClass().getCanonicalName()+" not registered");
        }
        ClzBindInfo clzBindInfo = mClzBindInfoHashMap.get(t.getClass());
        if(clzBindInfo == null || clzBindInfo.subTypeLinker == null){
            throw new RuntimeException(t.getClass().getCanonicalName()+" ClzBindInfo is null or subTypeLinker is null");
        }
        int subType = clzBindInfo.subTypeLinker.getSubType(t);
        return clzBindInfo.getBindedInfoByKey(subType);
    }

    @Override
    public int getItemViewType(Object t) {
        if(t == null){
            throw new RuntimeException("param t cannot be null");
        }
        if(!mClzBindInfoHashMap.containsKey(t.getClass())){
            throw new RuntimeException(t.getClass().getCanonicalName()+" not registered");
        }
        ClzBindInfo clzBindInfo = mClzBindInfoHashMap.get(t.getClass());
        if(clzBindInfo == null || clzBindInfo.subTypeLinker == null){
            throw new RuntimeException(t.getClass().getCanonicalName()+" ClzBindInfo is null or subTypeLinker is null");
        }
        int subType = clzBindInfo.subTypeLinker.getSubType(t);
        BindedInfo bindedInfo = clzBindInfo.getBindedInfoByKey(subType);

        if(bindedInfo == null){//说明还没有初始化,初始化一次
            ViewHolderBinder viewHolderBinder = clzBindInfo.subTypeLinker.onCreateViewHolderBinder(t);
            if(viewHolderBinder == null){
                throw new RuntimeException("onCreateViewHolderBinder cannot return null");
            }
            int itemViewType = mStartIndex;
            clzBindInfo.putBindedInfo(subType, new BindedInfo(mStartIndex, viewHolderBinder));
            mItemViewTypeBinderMap.put(itemViewType, viewHolderBinder);
            mStartIndex++;
            return itemViewType;
        }else{
            return bindedInfo.itemViewType;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(int itemViewType, ViewGroup parent) {
        ViewHolderBinder viewHolderBinder = mItemViewTypeBinderMap.get(itemViewType);
        if(viewHolderBinder == null){
            throw new RuntimeException("viewHolderBinder is null");
        }
        return viewHolderBinder.onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Object t, int position) {
        BindedInfo bindedInfo = getBindedInfo(t);
        if(bindedInfo == null || bindedInfo.viewHolderBinder == null){
            throw new RuntimeException("bindedInfo or bindedInfo.viewHolderBinder is null");
        }
        bindedInfo.viewHolderBinder.onBindViewHolder(viewHolder, t, position);
    }

    @Override
    public void registerType(Class<?> clz, ViewHolderBinder viewHolderBinder) {
        registerMultiType(clz, new DefaultSubTypeLinker<>(viewHolderBinder));
    }

    @Override
    public void registerMultiType(Class<?> clz, SubTypeLinker<?> subTypeLinker) {
        mClzBindInfoHashMap.remove(clz);
        ClzBindInfo clzBindInfo = new ClzBindInfo(subTypeLinker);
        mClzBindInfoHashMap.put(clz, clzBindInfo);

    }
}
