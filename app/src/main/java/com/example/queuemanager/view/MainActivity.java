package com.example.queuemanager.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.queuemanager.R;
import com.example.queuemanager.presenter.Presenter;

public class MainActivity extends AppCompatActivity {

    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new Presenter(this);
        Button fab = findViewById(R.id.button);
        fab.setOnClickListener(view -> presenter.buttonWasClicked());
    }
}
