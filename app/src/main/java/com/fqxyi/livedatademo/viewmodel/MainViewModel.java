package com.fqxyi.livedatademo.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.fqxyi.livedatademo.bean.MainBean;
import com.fqxyi.livedatademo.repository.MainRepository;

/**
 * ViewModel的好处是: 不会在由于configuration改变引起的onDestroy而销毁数据
 */
public class MainViewModel extends AndroidViewModel {

    private MainRepository mainRepository;
    //内存存储id，用于切换id，获取不同的请求结果，校验liveData的实现效果
    private int id = 0;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository(application);
    }

    /**
     * 如果有必要的话，可以使用{@link Transformations}执行数据转换操作
     */
    public LiveData<String> getData() {
        final MutableLiveData<MainBean> liveData = mainRepository.getData();
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
        if (id == 1) {
            id = 2;
        } else {
            id = 1;
        }
        mainRepository.loadData(id);
    }
}
