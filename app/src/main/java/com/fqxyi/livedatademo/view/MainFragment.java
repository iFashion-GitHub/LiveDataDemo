package com.fqxyi.livedatademo.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fqxyi.livedatademo.R;
import com.fqxyi.livedatademo.base.LifecycleFragment;
import com.fqxyi.livedatademo.observer.FragmentLifecycleObserver;
import com.fqxyi.livedatademo.viewmodel.MainViewModel;

public class MainFragment extends LifecycleFragment {

    //findView
    private Button mainFragmentBtn;
    private TextView mainFragmentText;
    //
    private MainViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        //findView
        mainFragmentBtn = (Button) rootView.findViewById(R.id.main_fragment_btn);
        mainFragmentText = (TextView) rootView.findViewById(R.id.main_fragment_text);
        //
        mainFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.getCurrentName().setValue("test");
            }
        });
        initObserver();
        initLifecycle();
        return rootView;
    }

    private void initObserver() {
//        mainViewModel.getData().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                mainFragmentText.setText(s);
//            }
//        });
        mainViewModel.getCurrentName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mainFragmentText.setText(s);
            }
        });
    }

    public void initLifecycle() {
        getLifecycle().addObserver(new FragmentLifecycleObserver(getActivity().getApplicationContext()));
    }
}