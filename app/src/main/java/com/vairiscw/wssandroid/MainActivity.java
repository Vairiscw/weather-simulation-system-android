package com.vairiscw.wssandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vairiscw.wssandroid.config.RetrofitClient;
import com.vairiscw.wssandroid.data.fan.FanAPI;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    Button fanButton;
    TextView windStatusTV;
    String windStatus = "off";
    private final String serverUrl = "server_url";
    FanAPI fanAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fanButton = findViewById(R.id.fan_button);
        windStatusTV = findViewById(R.id.windStatusID);
        windStatusTV.setText("-");
        windStatusTV.setText(windStatus);
        Retrofit retrofit = RetrofitClient.getClient(serverUrl);
        fanAPI = retrofit.create(FanAPI.class);

        fanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postStatus();
            }
        });
    }

    protected void getFan() {
        Call<ResponseBody> call = fanAPI.getStatus();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                windStatusTV.setText("-");
                Log.d("MainActivity", throwable.getMessage());
            }
        });
    }

    protected void postStatus() {

        Call<ResponseBody> call = fanAPI.postStatus(windStatus);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    windStatusTV.setText(windStatus);
                    Log.d("TAG", windStatus);
                    if (windStatus.equals("on")) windStatus = "off";
                    else if (windStatus.equals("off")) windStatus = "on";
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d("TAG", throwable.toString());
            }
        });
    }
}