package com.edus.gankio.net;

import com.edus.gankio.data.CommonResult;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService {
    private static final ApiService sInstance = new ApiService();

    private ApiService(){

    }

    public static ApiService getInstance(){
        return sInstance;
    }


    public void getGanHuoDate(DataCallback<CommonResult<List<String>>> callback){
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<CommonResult<List<String>>> ganHuoDate = gitApi.getGanHuoDate();
        ganHuoDate.enqueue(AdapterRestfulCallback.build(callback));
    }
}
