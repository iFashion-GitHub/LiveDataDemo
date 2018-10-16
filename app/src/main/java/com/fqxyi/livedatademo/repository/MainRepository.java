package com.fqxyi.livedatademo.repository;

import android.content.Context;

import com.fqxyi.livedatademo.apiservice.MainApiService;
import com.fqxyi.livedatademo.bean.MainBean;
import com.fqxyi.library.BaseRepository;
import com.fqxyi.network.RequestManager;
import com.fqxyi.network.bean.ErrorBean;
import com.fqxyi.network.tag.ReqTag;

import io.reactivex.Observable;

/**
 * Repository层
 * 处理从local storage、web service、database、...中得到的数据，并返回给viewModel
 */
public class MainRepository extends BaseRepository<MainBean> {

    //内存存储id，用于切换id，获取不同的请求结果，校验liveData的实现效果
    private int id = 0;

    public MainRepository(Context context) {
        super(context);
    }

    @Override
    public Observable<MainBean> getApiService() {
        if (id == 1) {
            id = 2;
        } else {
            id = 1;
        }
        return RequestManager.get().create(MainApiService.class).getData(id);
    }

    @Override
    public void responseSuccess(ReqTag reqTag, MainBean response) {
        liveData.setValue(response);
    }

    @Override
    public void responseError(ReqTag reqTag, ErrorBean errorBean) {
        liveData.setValue(null);
    }
}
