package com.edus.gankio.ui.adapter.multi;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.HashMap;

public class TypePoolImpl<Adapter extends RecyclerView.Adapter> implements TypePool<Adapter> {
    private Adapter mAdapter;
    private int mItemViewTypeIndex = 0;

    private static class BindedInfo{
        int itemViewType;
        ViewHolderBinder viewHolderBinder;
        public BindedInfo(int itemViewType, ViewHolderBinder viewHolderBinder){
            this.itemViewType = itemViewType;
            this.viewHolderBinder = viewHolderBinder;
        }
    }

    private static class ClzBindInfo<T> {
        HashMap<String, BindedInfo> subTypeBindedMap;
        SubTypeLinker<T> subTypeLinker;
        public ClzBindInfo(SubTypeLinker<T> subTypeLinker){
            this.subTypeLinker = subTypeLinker;
            subTypeBindedMap = new HashMap<>();
        }
        public BindedInfo getBindedInfoByKey(String subType){
            return subTypeBindedMap.get(subType);
        }
        public void putBindedInfo(String subType, BindedInfo bindedInfo){
            subTypeBindedMap.put(subType, bindedInfo);
        }
    }

    private HashMap<Class, ClzBindInfo> mClzBindInfoHashMap;
    private HashMap<Integer, ViewHolderBinder> mItemViewTypeBinderMap;

    public TypePoolImpl(int itemViewTypeIndex, Adapter adapter){
        mClzBindInfoHashMap = new HashMap<>();
        mItemViewTypeBinderMap = new HashMap<>();
        if(itemViewTypeIndex < 0 ){
            throw new RuntimeException("start index cannot be null");
        }
        if(adapter == null){
            throw new RuntimeException("adapter cannot be nulls");
        }
        mItemViewTypeIndex = itemViewTypeIndex;
        mAdapter = adapter;
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
        String subType = clzBindInfo.subTypeLinker.getSubType(t);
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
        String subType = clzBindInfo.subTypeLinker.getSubType(t);
        BindedInfo bindedInfo = clzBindInfo.getBindedInfoByKey(subType);

        if(bindedInfo == null){//说明还没有初始化,初始化一次
            int itemViewType = mItemViewTypeIndex;
            ViewHolderBinder viewHolderBinder = clzBindInfo.subTypeLinker.onCreateViewHolderBinder(subType, t);
            if(viewHolderBinder == null){
                throw new RuntimeException("onCreateViewHolderBinder cannot return null");
            }
            viewHolderBinder.mAdapter = mAdapter;
            clzBindInfo.putBindedInfo(subType, new BindedInfo(itemViewType, viewHolderBinder));
            mItemViewTypeBinderMap.put(itemViewType, viewHolderBinder);
            mItemViewTypeIndex++;
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
    public <T> void registerType(Class<T> clz, ViewHolderBinder<RecyclerView.ViewHolder, T> viewHolderBinder) {
        registerMultiType(clz, new DefaultSubTypeLinker<T>(viewHolderBinder));
    }

    @Override
    public <T> void registerMultiType(Class<T> clz, SubTypeLinker<T> subTypeLinker) {
        if(clz == null || subTypeLinker == null){
            throw new RuntimeException("clz or subTypeLinked cannot be null");
        }
        mClzBindInfoHashMap.remove(clz);
        ClzBindInfo clzBindInfo = new ClzBindInfo(subTypeLinker);
        mClzBindInfoHashMap.put(clz, clzBindInfo);

    }
}
