package com.edus.gankio.net;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterRestfulCallback<T> implements Callback<T> {
    private DataCallback<T> mDataCallback;

    private AdapterRestfulCallback(DataCallback<T> callback){
        mDataCallback = callback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(mDataCallback != null){
            mDataCallback.onReceived(response.body());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if(mDataCallback != null){
            mDataCallback.onException(t);
        }
    }

    public static <T> AdapterRestfulCallback<T> build(DataCallback<T> callback){
        return new AdapterRestfulCallback<T>(callback);
    }
}
