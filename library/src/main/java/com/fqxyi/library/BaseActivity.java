package com.fqxyi.library;

import android.support.v4.app.FragmentActivity;

public abstract class BaseActivity<T extends BaseViewModel> extends FragmentActivity {

    protected T viewModel;

    public BaseActivity() {
        viewModel = getViewModel();
    }

    protected abstract T getViewModel();
}
