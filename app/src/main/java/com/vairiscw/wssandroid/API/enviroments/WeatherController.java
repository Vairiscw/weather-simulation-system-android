package com.vairiscw.wssandroid.API.enviroments;

import android.util.Log;

import com.vairiscw.wssandroid.API.ConnectionInformation;
import com.vairiscw.wssandroid.API.callbacks.WeatherCallback;
import com.vairiscw.wssandroid.data.environment.Weather;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherController {
    WeatherAPI api;
    ConnectionInformation information;

    public WeatherController() {
        information = new ConnectionInformation();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(information.getServerUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(WeatherAPI.class);
    }

    public void getAllWeathers(WeatherCallback callback) {
        Call<List<Weather>> call = api.getWeathers();
        call.enqueue(new Callback<List<Weather>>() {
            @Override
            public void onResponse(Call<List<Weather>> call, Response<List<Weather>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onWeatherDataReceived(response.body());
                    Log.d("WeatherController", "onWeatherResponse: " + response.body().toString());
                } else {
                    callback.onWeatherDataReceived(Collections.emptyList());
                    Log.d("WeatherController", "onWeatherResponse: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Weather>> call, Throwable t) {
                Log.d("WeatherController", "onWeatherFailure: " + t.toString());
                callback.onWeatherDataReceived(Collections.emptyList());
            }
        });
    }
}
