package com.vairiscw.wssandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.adapters.ListRecyclerAdapter;
import com.vairiscw.wssandroid.config.RetrofitClient;
import com.vairiscw.wssandroid.API.fan.FanAPI;
import com.vairiscw.wssandroid.data.scenes.ScenesAPI;
import com.vairiscw.wssandroid.data.times.TimesAPI;
import com.vairiscw.wssandroid.view.environment_page.EnvironmentPage;
import com.vairiscw.wssandroid.view.environment_page.NoPaddingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView weatherRecyclerView;
    List<EnvironmentPage> weatherList;
    int[] weatherIcons = {R.drawable.sun_icon, R.drawable.clouds_icon, R.drawable.rain_icon};
    String[] weatherTitles = {"Ясно", "Пасмурно", "Дождь"};
    ListRecyclerAdapter weatherRecyclerAdapter;

    RecyclerView soundsRecyclerView;
    List<EnvironmentPage> soundsList;
    int[] soundsIcons = {R.drawable.creature_icon, R.drawable.water_icon, R.drawable.birds_icon, R.drawable.fireplace_icon};
    String[] soundsTitles = {"Сверчки", "Вода", "Птицы", "Камин"};
    ListRecyclerAdapter soundsRecyclerAdapter;

    ImageView sunIcon;
    TextView sunText;
    Button timeSunButton;

    ImageView nightIcon;
    TextView nightText;
    Button timeNightButton;

    private final String serverUrl = "http://192.168.27.134:9898";
    FanAPI fanAPI;
    ScenesAPI scenesAPI;
    TimesAPI timesAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherRecycleSetup();
        timeButtonSetup();
        soundsRecyclerSetup();

        Retrofit retrofit = RetrofitClient.getClient(serverUrl);
        fanAPI = retrofit.create(FanAPI.class);
        scenesAPI = retrofit.create(ScenesAPI.class);
        timesAPI = retrofit.create(TimesAPI.class);

    }



//    protected void postStatus() {
//
//        Call<ResponseBody> call = fanAPI.postStatus(windStatus);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccessful()) {
//                    windStatusTV.setText(windStatus);
//                    Log.d("TAG", windStatus);
//                    if (windStatus.equals("on")) windStatus = "off";
//                    else if (windStatus.equals("off")) windStatus = "on";
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
//                Log.d("TAG", throwable.getMessage());
//                finish();
//            }
//        });
//    }}

    protected void weatherRecycleSetup() {
        weatherList = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            weatherList.add(new EnvironmentPage(weatherIcons[i], weatherTitles[i]));
        }

        weatherRecyclerView = findViewById(R.id.weatherRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        weatherRecyclerView.setLayoutManager(layoutManager);
        weatherRecyclerView.addItemDecoration(new NoPaddingItemDecoration());
        weatherRecyclerAdapter = new ListRecyclerAdapter(weatherList, this);
        weatherRecyclerView.setAdapter(weatherRecyclerAdapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(weatherRecyclerView);

        weatherRecyclerView.scrollToPosition(Integer.MAX_VALUE / 2);
        weatherRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View snappedView = snapHelper.findSnapView(layoutManager);
                    if (snappedView != null) {
                        int position = recyclerView.getChildAdapterPosition(snappedView);
                        weatherRecyclerAdapter.setActivePosition(position);
                    }
                }
            }
        });

    }

    protected void timeButtonSetup() {
        sunIcon = findViewById(R.id.timeSunIcon);
        sunText = findViewById(R.id.timeSunText);
        sunIcon.setImageResource(R.drawable.sun_icon);
        sunIcon.setColorFilter(ContextCompat.getColor(this.getApplicationContext(), R.color.disable_icon_color));
        timeSunButton = findViewById(R.id.timeSunButton);
        timeSunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sunIcon.clearColorFilter();
                sunIcon.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.active_sun_icon_color));
                sunText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.active_sun_icon_color));
                //nightIcon.clearColorFilter();
                nightIcon.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.disable_icon_color));
                nightText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.disable_icon_color));
            }
        });

        nightIcon = findViewById(R.id.timeNightIcon);
        nightText = findViewById(R.id.timeNightText);
        nightIcon.setImageResource(R.drawable.night_icon);
        nightIcon.setColorFilter(ContextCompat.getColor(this.getApplicationContext(), R.color.disable_icon_color));
        timeNightButton = findViewById(R.id.timeNightButton);
        timeNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nightIcon.clearColorFilter();
                nightIcon.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.text_color));
                nightText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.text_color));
                //sunIcon.clearColorFilter();
                sunIcon.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.disable_icon_color));
                sunText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.disable_icon_color));
            }
        });
    }

    protected void soundsRecyclerSetup() {
        soundsList = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            soundsList.add(new EnvironmentPage(soundsIcons[i], soundsTitles[i]));
        }

        soundsRecyclerView = findViewById(R.id.soundsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        soundsRecyclerView.setLayoutManager(layoutManager);
        soundsRecyclerView.addItemDecoration(new NoPaddingItemDecoration());
        soundsRecyclerAdapter = new ListRecyclerAdapter(soundsList, this);
        soundsRecyclerView.setAdapter(soundsRecyclerAdapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(soundsRecyclerView);

        soundsRecyclerView.scrollToPosition(Integer.MAX_VALUE / 2);
        soundsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View snappedView = snapHelper.findSnapView(layoutManager);
                    if (snappedView != null) {
                        int position = recyclerView.getChildAdapterPosition(snappedView);
                        soundsRecyclerAdapter.setActivePosition(position);
                    }
                }
            }
        });
    }
}
