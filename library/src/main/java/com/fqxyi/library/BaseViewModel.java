package com.fqxyi.library;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

public abstract class BaseViewModel<T extends BaseRepository> extends AndroidViewModel {

    protected Context context;

    protected T repository;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        context = application;
        repository = getRepository();
    }

    protected abstract T getRepository();
}
