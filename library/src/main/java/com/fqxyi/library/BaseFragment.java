package com.fqxyi.library;

import android.content.Context;
import android.support.v4.app.Fragment;

public abstract class BaseFragment<T extends BaseViewModel> extends Fragment {

    protected T viewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (viewModel == null) {
            viewModel = getViewModel();
        }
    }

    protected abstract T getViewModel();
}
