package com.vairiscw.wssandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vairiscw.wssandroid.config.RetrofitClient;
import com.vairiscw.wssandroid.data.fan.FanAPI;
import com.vairiscw.wssandroid.data.temperature.TemperatureAPI;
import com.vairiscw.wssandroid.data.temperature.TemperatureData;
import com.vairiscw.wssandroid.view.RecycleAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    Button openButton;
    Button closeButton;
    ConstraintLayout slideLayout;
    RecyclerView recView;

    Button fanButton;
    TextView windStatusTV;
    String windStatus = "off";
    private final String serverUrl = "serverURL";
    FanAPI fanAPI;
    TemperatureAPI temperatureAPI;
    RecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openButton = findViewById(R.id.openSlide);
        slideLayout = findViewById(R.id.Slide);
        closeButton = findViewById(R.id.closeSlide);
        recView = findViewById(R.id.recycler_view);
        recView.setLayoutManager(new LinearLayoutManager(this));

        fanButton = findViewById(R.id.fan_button);
        windStatusTV = findViewById(R.id.windStatusID);
        windStatusTV.setText("-");
        windStatusTV.setText(windStatus);

        Retrofit retrofit = RetrofitClient.getClient(serverUrl);
        fanAPI = retrofit.create(FanAPI.class);
        temperatureAPI = retrofit.create(TemperatureAPI.class);

        fanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postStatus();
            }
        });

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideLayout.setVisibility(View.VISIBLE);
                getTemperatures();
                closeButton.setEnabled(true);
                openButton.setEnabled(false);
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideLayout.setVisibility(View.INVISIBLE);
                closeButton.setEnabled(false);
                openButton.setEnabled(true);
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

    protected void getTemperatures() {
        Call<List<TemperatureData>> call = temperatureAPI.getTemperatures();
        call.enqueue(new Callback<List<TemperatureData>>() {
            @Override
            public void onResponse(Call<List<TemperatureData>> call, Response<List<TemperatureData>> response) {
                if (response.isSuccessful()) {
                    List<TemperatureData> dataList = response.body();
                    adapter = new RecycleAdapter(dataList);
                    recView.setAdapter(adapter);
                    Log.d("MainActivity", dataList.toString());
                } else {
                    Log.e("MainActivity", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<TemperatureData>> call, Throwable t) {
                Log.e("MainActivity", "Request failed: " + t.getMessage());
            }
        });
    }
}