package com.vairiscw.wssandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vairiscw.wssandroid.API.check.CheckController;
import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.API.callbacks.CheckCallback;

public class StartActivity extends AppCompatActivity {
    Button connectionButton;
    Button informationButton;
    CheckController controller;
    boolean connection = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_start);

        connectionButton = findViewById(R.id.connectButton);
        informationButton = findViewById(R.id.informationButton);

        controller = new CheckController();
        connectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.tryConnect(new CheckCallback() {
                    @Override
                    public void onConnectionChecked(Boolean result) {
                        if (result) {
                            startActivity(new Intent(StartActivity.this, MainActivity.class));
                        }
                    }
                });

            }
        });
    }
}
