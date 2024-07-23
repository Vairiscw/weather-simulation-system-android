package com.vairiscw.wssandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.config.RetrofitClient;
import com.vairiscw.wssandroid.API.fan.FanAPI;
import com.vairiscw.wssandroid.data.scenes.Scenes;
import com.vairiscw.wssandroid.data.scenes.ScenesAPI;
import com.vairiscw.wssandroid.data.scenes.ScenesAdapter;
import com.vairiscw.wssandroid.data.times.Times;
import com.vairiscw.wssandroid.data.times.TimesAPI;
import com.vairiscw.wssandroid.data.times.TimesAdapter;

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
    TimesAdapter timesAdapter;
    ScenesAdapter scenesAdapter;
    Button fanButton;
    TextView windStatusTV;
    String windStatus = "off";
    private final String serverUrl = "http://192.168.27.134:9898";
    FanAPI fanAPI;
    ScenesAPI scenesAPI;
    TimesAPI timesAPI;
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
        scenesAPI = retrofit.create(ScenesAPI.class);
        timesAPI = retrofit.create(TimesAPI.class);
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
                closeButton.setEnabled(true);
                openButton.setEnabled(false);
                printTimes();
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

    protected void printScenes() {
        Call<List<Scenes>> call = scenesAPI.getScenes();
        call.enqueue(new Callback<List<Scenes>>() {
            @Override
            public void onResponse(Call<List<Scenes>> call, Response<List<Scenes>> response) {
                List<Scenes> scenesList = response.body();
                scenesAdapter = new ScenesAdapter(scenesList);
                recView.setAdapter(scenesAdapter);
            }

            @Override
            public void onFailure(Call<List<Scenes>> call, Throwable throwable) {
                Log.d("TAG", throwable.getMessage());
            }
        });
    }

    protected void printTimes() {
        Call<List<Times>> call = timesAPI.getTimes();

        call.enqueue(new Callback<List<Times>>() {
            @Override
            public void onResponse(Call<List<Times>> call, Response<List<Times>> response) {
                List<Times> timesList = response.body();
                Log.d("PRINT", timesList.toString());
                timesAdapter = new TimesAdapter(timesList);
                recView.setAdapter(timesAdapter);
            }

            @Override
            public void onFailure(Call<List<Times>> call, Throwable throwable) {
                Log.d("TAG", throwable.getMessage());
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
                Log.d("TAG", throwable.getMessage());
                finish();
            }
        });
    }}

