package com.fqxyi.livedatademo.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.fqxyi.livedatademo.apiservice.MainApiService;
import com.fqxyi.livedatademo.bean.MainBean;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Repository层
 * 处理从local storage、web service、database、...中得到的数据，并返回给viewModel
 */
public class MainRepository {

    private Context context;
    //
    private Retrofit retrofit;
    //
    private final MutableLiveData<MainBean> liveData;

    public MainRepository(Context context) {
        this.context = context;
        liveData = new MutableLiveData<>();
        initRetrofit();
    }

    private void initRetrofit() {
        synchronized (MainRepository.this) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getDir("http_cache", Context.MODE_PRIVATE), 1024 * 1024 * 100))
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    //设置进行连接失败重试
                    .retryOnConnectionFailure(false)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://104.224.173.101:8080")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    /**
     * 请求数据
     */
    public void loadData(int id) {
        MainApiService mainApiService = retrofit.create(MainApiService.class);
        Call<MainBean> call = mainApiService.getData(id);
        call.enqueue(new Callback<MainBean>() {
            @Override
            public void onResponse(Call<MainBean> call, Response<MainBean> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MainBean> call, Throwable t) {
                liveData.setValue(null);
            }
        });
    }

    /**
     * 获取数据
     */
    public MutableLiveData<MainBean> getData() {
        return liveData;
    }

}
