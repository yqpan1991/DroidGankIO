package com.edus.gankio.net;

import com.edus.gankio.data.CommonResource;
import com.edus.gankio.data.CommonResult;
import com.edus.gankio.data.DailyItem;
import com.edus.gankio.data.XianduCategoryItem;
import com.edus.gankio.data.XianduData;
import com.edus.gankio.data.XianduSubCategoryItem;
import com.edus.gankio.library.utils.Singleton;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiService {

    private static Singleton<ApiService> mSingleton = new Singleton<ApiService>() {
        @Override
        protected ApiService create() {
            return new ApiService();
        }
    };

    private ApiService(){

    }

    public static ApiService getInstance(){
        return mSingleton.get();
    }


    public void getGanHuoDate(DataCallback<CommonResult<List<String>>> callback){
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<CommonResult<List<String>>> ganHuoDate = gitApi.getGanHuoDate();
        ganHuoDate.enqueue(AdapterRestfulCallback.build(callback));
    }

    public void getHistoryList(DataCallback<CommonResult<List<DailyItem>>> callback, int pageSize, int pageIndex){
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<CommonResult<List<DailyItem>>> ganHuoDate = gitApi.getHistoryContent(pageSize, pageIndex);
        ganHuoDate.enqueue(AdapterRestfulCallback.build(callback));
    }

    public void getAndroidResource(DataCallback<CommonResult<List<CommonResource>>> callback, int pageSize, int pageIndex){
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/data/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<CommonResult<List<CommonResource>>> android = gitApi.getResource("Android", pageSize, pageIndex);
        android.enqueue(AdapterRestfulCallback.build(callback));
    }

    public void getIOSResource(DataCallback<CommonResult<List<CommonResource>>> callback, int pageSize, int pageIndex){
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/data/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<CommonResult<List<CommonResource>>> android = gitApi.getResource("iOS", pageSize, pageIndex);
        android.enqueue(AdapterRestfulCallback.build(callback));
    }

    public void getFrontResource(DataCallback<CommonResult<List<CommonResource>>> callback, int pageSize, int pageIndex){
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/data/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<CommonResult<List<CommonResource>>> android = gitApi.getResource("前端", pageSize, pageIndex);
        android.enqueue(AdapterRestfulCallback.build(callback));
    }

    public void getAppResource(DataCallback<CommonResult<List<CommonResource>>> callback, int pageSize, int pageIndex){
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/data/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<CommonResult<List<CommonResource>>> android = gitApi.getResource("App", pageSize, pageIndex);
        android.enqueue(AdapterRestfulCallback.build(callback));
    }

    public void getBonusResource(DataCallback<CommonResult<List<CommonResource>>> callback, int pageSize, int pageIndex){
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/data/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<CommonResult<List<CommonResource>>> android = gitApi.getResource("福利", pageSize, pageIndex);
        android.enqueue(AdapterRestfulCallback.build(callback));
    }

    public void getVideoResource(DataCallback<CommonResult<List<CommonResource>>> callback, int pageSize, int pageIndex){
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/data/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<CommonResult<List<CommonResource>>> android = gitApi.getResource("休息视频", pageSize, pageIndex);
        android.enqueue(AdapterRestfulCallback.build(callback));
    }

    public void getRecommendResource(DataCallback<CommonResult<List<CommonResource>>> callback, int pageSize, int pageIndex){
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/data/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<CommonResult<List<CommonResource>>> android = gitApi.getResource("瞎推荐", pageSize, pageIndex);
        android.enqueue(AdapterRestfulCallback.build(callback));
    }

    public void getXianduCategories(DataCallback<CommonResult<List<XianduCategoryItem>>> callback){
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<CommonResult<List<XianduCategoryItem>>> android = gitApi.getXianduCategories();
        android.enqueue(AdapterRestfulCallback.build(callback));
    }

    public void getXianduSubCategoryList(String engName, DataCallback<CommonResult<List<XianduSubCategoryItem>>> callback){
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<CommonResult<List<XianduSubCategoryItem>>> android = gitApi.getXianduSubCategoryList(engName);
        android.enqueue(AdapterRestfulCallback.build(callback));
    }

    public void getXianduListResource(String categoryId, int pageSize, int pageIndex, DataCallback<CommonResult<List<XianduData>>> callback){
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gank.io/api/").addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);

        Call<CommonResult<List<XianduData>>> android = gitApi.getXianduData(categoryId, pageSize, pageIndex);
        android.enqueue(AdapterRestfulCallback.build(callback));
    }
}
