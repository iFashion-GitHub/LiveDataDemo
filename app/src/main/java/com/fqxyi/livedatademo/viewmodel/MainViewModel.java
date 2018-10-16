package com.fqxyi.livedatademo.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.fqxyi.livedatademo.bean.MainBean;
import com.fqxyi.library.BaseViewModel;
import com.fqxyi.livedatademo.repository.MainRepository;

/**
 * ViewModel层
 * 负责提供View层需要的接口，执行数据转换的逻辑
 * 好处: 不会在由于configuration改变引起的onDestroy而销毁数据
 */
public class MainViewModel extends BaseViewModel<MainRepository> {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 如果有必要的话，可以使用{@link Transformations}执行数据转换操作
     */
    public LiveData<String> getData() {
        final MutableLiveData<MainBean> liveData = repository.getData();
        //执行数据转换操作并获得转换后的数据
        LiveData<String> newLiveData = Transformations.switchMap(liveData, new Function<MainBean, LiveData<String>>() {
            @Override
            public LiveData<String> apply(MainBean mainBean) {
                final MutableLiveData<String> newLiveData = new MutableLiveData<>();
                if (mainBean == null || mainBean.data == null) {
                    newLiveData.setValue(null);
                } else {
                    newLiveData.setValue(mainBean.data.name);
                }
                return newLiveData;
            }
        });
        //返回转换后的数据
        return newLiveData;
    }

    /**
     * 发起请求
     */
    public void loadData() {
        repository.loadData();
    }

    @Override
    protected MainRepository getRepository() {
        return new MainRepository(context);
    }
}
