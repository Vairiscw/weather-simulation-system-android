package com.vairiscw.wssandroid.API.callbacks;

import com.vairiscw.wssandroid.data.environment.Weather;

import java.util.List;

public interface WeatherCallback {
    void onWeatherDataReceived(List<Weather> weatherList);
}