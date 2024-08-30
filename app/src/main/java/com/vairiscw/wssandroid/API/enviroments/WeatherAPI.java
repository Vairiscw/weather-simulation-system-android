package com.vairiscw.wssandroid.API.enviroments;

import com.vairiscw.wssandroid.data.environment.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherAPI {
    @GET("/components/weather")
    Call<List<Weather>> getWeathers();
}
