package com.fqxyi.livedatademo.apiservice;

import com.fqxyi.livedatademo.bean.MainBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApiService {

    @GET("/firstmybatis/queryName")
    Call<MainBean> getData(@Query("id") int id);

}
