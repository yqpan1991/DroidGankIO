package com.edus.gankio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.edus.gankio.data.Demo;
import com.edus.gankio.net.GitApi;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    String API = "http://gank.io/api/data/Android/";

    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mTvContent = (TextView) findViewById(R.id.tv_content);
    }

    private void initData(){
       /* Retrofit retrofit = new Retrofit.Builder().baseUrl(API).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<ResponseBody> call = gitApi.getFeed(2, 1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String result = null;
                if(response.body() != null){
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, "onResponse:"+result);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure");
            }
        });*/

        testRetrofitWithConverter();


    }

    private void testRetrofitWithConverter() {
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API).addConverterFactory(GsonConverterFactory.create(gson)).build();
        GitApi gitApi = retrofit.create(GitApi.class);
        Call<Demo> call = gitApi.getFeed();
        call.enqueue(new Callback<Demo>() {
            @Override
            public void onResponse(Call<Demo> call, Response<Demo> response) {
                Log.e(TAG, "onResponse from converter:"+response.body());
            }

            @Override
            public void onFailure(Call<Demo> call, Throwable t) {
                Log.e(TAG, "onResponse from converter:"+t.toString());
            }
        });

    }
}
