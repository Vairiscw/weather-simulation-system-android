package com.vairiscw.wssandroid.view.setup;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.vairiscw.wssandroid.API.enviroments.WeatherController;
import com.vairiscw.wssandroid.API.template.TemplateController;
import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.API.callbacks.WeatherCallback;
import com.vairiscw.wssandroid.data.environment.Weather;
import com.vairiscw.wssandroid.view.adapters.SmallListRecyclerAdapter;
import com.vairiscw.wssandroid.view.environment_page.SmallEnvironmentPage;

import java.util.ArrayList;
import java.util.List;

public class WeatherPageViewSetup {
    View mainView;
    ViewPager2 weatherRecyclerView;
    List<SmallEnvironmentPage> weatherList;
    int[] weatherIcons = {R.drawable.sun_icon, R.drawable.clouds_icon, R.drawable.rain_icon};
    List<Weather> weathersFromServer;
    SmallListRecyclerAdapter weatherCoverAdapter;
    TemplateController templateController;
    WeatherController weatherController;

    public WeatherPageViewSetup(View mainView, TemplateController templateController) {
        this.templateController = templateController;
        this.weatherController = new WeatherController();
        this.mainView = mainView;
        weatherController.getAllWeathers(new WeatherCallback() {
            @Override
            public void onWeatherDataReceived(List<Weather> weatherList) {
                weathersFromServer = weatherList;
                weatherRecycleSetup();
            }
        });

    }

    public void weatherRecycleSetup() {


        weatherList = new ArrayList<>();
        for (int i = 0; i < weathersFromServer.size(); ++i) {
            weatherList.add(new SmallEnvironmentPage(weatherIcons[i], weathersFromServer.get(i).getDesignation()));
        }

        weatherRecyclerView = mainView.findViewById(R.id.weatherRecyclerView);
        weatherCoverAdapter = new SmallListRecyclerAdapter(weatherList, mainView.getContext());
        weatherRecyclerView.setAdapter(weatherCoverAdapter);

        weatherRecyclerView.setCurrentItem((Math.toIntExact(templateController.getCurrentWeatherId())) + weathersFromServer.size() * 2000 - 1);
        weatherCoverAdapter.setActivePosition((Math.toIntExact(templateController.getCurrentWeatherId())) + weathersFromServer.size() * 2000 - 1);

        weatherRecyclerView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                weatherCoverAdapter.setActivePosition(position);
                changeCurrentWeather(position % weathersFromServer.size());
            }
        });

        int padding = 50;
        weatherRecyclerView.setPadding(padding, 0, padding, 0);
        weatherRecyclerView.setClipToPadding(false);
        weatherRecyclerView.setClipChildren(false);
        weatherRecyclerView.setOffscreenPageLimit(1);

        weatherRecyclerView.setPageTransformer(new ViewPager2.PageTransformer()
        {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float absPos = Math.abs(position);

                if (position < -1) {
                    page.setAlpha(0f);
                } else if (position <= 1) {
                    page.setAlpha(1f);
                    page.setTranslationX(-position * page.getWidth());
                    float scaleFactor = 0.85f + (1 - absPos) * 0.15f;
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                } else {
                    page.setAlpha(0f);
                }
            }
        });


    }

    private void changeCurrentWeather(int position) {
        Log.d("TAG", "changeCurrentWeather:" + position + " ");

        templateController.updateWeather(weathersFromServer.get(position));
    }
}
