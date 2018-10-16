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
import com.fqxyi.library.BaseFragment;
import com.fqxyi.livedatademo.observer.FragmentLifecycleObserver;
import com.fqxyi.livedatademo.viewmodel.MainViewModel;

/**
 * View层
 * 只负责显示UI，不执行数据处理逻辑
 */
public class MainFragment extends BaseFragment<MainViewModel> {

    //findView
    private Button mainFragmentBtn;
    private TextView mainFragmentText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        //findView
        mainFragmentBtn = (Button) rootView.findViewById(R.id.main_fragment_btn);
        mainFragmentText = (TextView) rootView.findViewById(R.id.main_fragment_text);
        //
        initEvent();
        initObserver();
        initLifecycle();
        return rootView;
    }

    private void initEvent() {
        mainFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.loadData();
            }
        });
    }

    private void initObserver() {
        viewModel.getData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mainFragmentText.setText(s);
            }
        });
    }

    public void initLifecycle() {
        getLifecycle().addObserver(new FragmentLifecycleObserver(getActivity().getApplicationContext()));
    }

    @Override
    protected MainViewModel getViewModel() {
        return ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }
}
