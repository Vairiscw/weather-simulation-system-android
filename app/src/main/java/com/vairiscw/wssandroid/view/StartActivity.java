package com.vairiscw.wssandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vairiscw.wssandroid.API.check.CheckAPI;
import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.config.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StartActivity extends AppCompatActivity {
    Button connectionButton;
    Button informationButton;
    private final String serverUrl = "http://192.168.27.134:9898";
    CheckAPI checkAPI;
    boolean connection = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_start);

        connectionButton = findViewById(R.id.connectButton);
        informationButton = findViewById(R.id.informationButton);

        Retrofit retrofit = RetrofitClient.getClient(serverUrl);
        checkAPI = retrofit.create(CheckAPI.class);
        connectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected()) { // Для теста
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(StartActivity.this, "Не удалось подключиться",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected boolean isConnected() {
        Call<ResponseBody> call = checkAPI.checkingConnection();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.d("StartActivity", "Successful");
                    connection = true;
                }
                else {
                    Log.d("StartActivity", "What");
                    connection = false;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d("TAG", throwable.getMessage());Log.d("TAG", call.request().toString());
                connection = false;
            }
        });


        return connection;
    }
}
