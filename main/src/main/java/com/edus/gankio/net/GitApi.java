package com.edus.gankio.net;

import com.edus.gankio.data.CommonResource;
import com.edus.gankio.data.CommonResult;
import com.edus.gankio.data.Demo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by panda on 2017/5/8.
 */

public interface GitApi {
    @GET("{pageSize}/{pageIndex}")
    public Call<ResponseBody> getFeed(@Path("pageSize") int pageSize, @Path("pageIndex") int pageIndex);
    // string user is for passing values from edittext for eg: user=basil2style,google
    // response is the response from the server which is now in the POJO

    @GET("2/1")
    public Call<Demo> getFeed();

    @GET("day/history")
    Call<CommonResult<List<String>>> getGanHuoDate();

    @GET("{resourceType}/{pageSize}/{pageIndex}")
    public Call<CommonResult<List<CommonResource>>> getResource(@Path("resourceType") String resourceType, @Path("pageSize") int pageSize, @Path("pageIndex") int pageIndex);

}
