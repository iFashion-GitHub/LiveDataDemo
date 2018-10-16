package com.fqxyi.livedatademo.apiservice;

import com.fqxyi.livedatademo.bean.MainBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * ApiService
 */
public interface MainApiService {

    @GET("/firstmybatis/queryName")
    Observable<MainBean> getData(@Query("id") int id);

}
