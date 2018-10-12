package com.fqxyi.livedatademo.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.fqxyi.livedatademo.repository.MainRepository;
import com.fqxyi.livedatademo.bean.MainBean;

/**
 * ViewModel的好处是: 不会在由于configuration改变引起的onDestroy而销毁数据
 */
public class MainViewModel extends AndroidViewModel {

    private MainRepository mainRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = new MainRepository(application);
    }

    private MutableLiveData<String> mCurrentName;

    public MutableLiveData<String> getCurrentName() {
        if (mCurrentName == null) {
            mCurrentName = new MutableLiveData<String>();
        }
        return mCurrentName;
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
}
