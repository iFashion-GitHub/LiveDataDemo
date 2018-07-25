package com.fqxyi.livedatademo;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    private TextView textView;
    private Button button;

    private NameViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //findView
        textView = (TextView) findViewById(R.id.text_view);
        button = (Button) findViewById(R.id.button);
        //switch click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel.getCurrentName().getValue() != null && mModel.getCurrentName().getValue().equals("fqxyi")) {
                    mModel.getCurrentName().setValue("hello");
                } else {
                    mModel.getCurrentName().setValue("fqxyi");
                }
            }
        });
        //createViewModel
        createViewModel();
    }

    private void createViewModel() {
        // Get the ViewModel.
        mModel = ViewModelProviders.of(this).get(NameViewModel.class);
        // Create the observer which updates the UI.
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                // Update the UI, in this case, a TextView.
                textView.setText(newName);
            }
        };
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mModel.getCurrentName().observe(this, nameObserver);
    }
}
