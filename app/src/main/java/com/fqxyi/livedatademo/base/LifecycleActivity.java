package com.fqxyi.livedatademo.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

/**
 * Activity基类
 */
public class LifecycleActivity extends FragmentActivity {

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @NonNull
    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
