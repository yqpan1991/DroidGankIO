package com.edus.gankio.library.utils;

public abstract class Singleton<T> {
    private  T mInstance;

    protected abstract T create();

    public final T get(){
        if(mInstance == null){
            synchronized (this){
                if(mInstance == null){
                    mInstance = create();
                }
            }
        }
        return mInstance;
    }

}
