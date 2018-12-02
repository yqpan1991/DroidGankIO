package com.edus.gankio.cache;

import android.text.TextUtils;

import com.edus.gankio.data.XianduData;
import com.edus.gankio.data.XianduSubCategoryItem;
import com.edus.gankio.library.utils.Singleton;

import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {
    private static Singleton<CacheManager> sSingleton = new Singleton<CacheManager>() {
        @Override
        protected CacheManager create() {
            return new CacheManager();
        }
    } ;

    private ConcurrentHashMap<String, MemoryCache<XianduData>> mXianduCacheMap;
    private ConcurrentHashMap<String, MemoryCache<XianduSubCategoryItem>> mSubCategoryMap;


    private CacheManager(){
        mXianduCacheMap = new ConcurrentHashMap<>();
        mSubCategoryMap = new ConcurrentHashMap<>();
    }

    public static CacheManager getInstance(){
        return sSingleton.get();
    }

    public void putXianDuCache(String key, MemoryCache<XianduData> cache){
        if(TextUtils.isEmpty(key)){
            return;
        }
        if(cache == null){
            mXianduCacheMap.remove(key);
        }
        mXianduCacheMap.put(key, cache);
    }

    public MemoryCache<XianduData> getXianDuCache(String key){
        if(TextUtils.isEmpty(key)){
            return null;
        }
        return mXianduCacheMap.get(key);
    }

    public void putSubCategoryCache(String key, MemoryCache<XianduSubCategoryItem> cache){
        if(TextUtils.isEmpty(key)){
            return;
        }
        if(cache == null){
            mSubCategoryMap.remove(key);
        }
        mSubCategoryMap.put(key, cache);
    }

    public MemoryCache<XianduSubCategoryItem> getSubCategoryCache(String key){
        if(TextUtils.isEmpty(key)){
            return null;
        }
        return mSubCategoryMap.get(key);
    }

    public void clearAll(){
        mXianduCacheMap.clear();
        mSubCategoryMap.clear();
    }






}
