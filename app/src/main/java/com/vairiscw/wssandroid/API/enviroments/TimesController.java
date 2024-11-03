package com.vairiscw.wssandroid.API.enviroments;

import android.util.Log;

import com.vairiscw.wssandroid.API.ConnectionInformation;
import com.vairiscw.wssandroid.API.callbacks.TimeCallback;
import com.vairiscw.wssandroid.data.environment.Time;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimesController {
    TimesAPI api;
    ConnectionInformation information;
    List<Time> allTimes;

    public TimesController() {
        information = new ConnectionInformation();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(information.getServerUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(TimesAPI.class);
    }

    public void getAllTimes(TimeCallback callback) {
        Call<List<Time>> call = api.getTimes();
        call.enqueue(new Callback<List<Time>>() {
            @Override
            public void onResponse(Call<List<Time>> call, Response<List<Time>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onTimeDataReceived(response.body());
                    Log.d("TimeController", "onTimeResponse: " + response.body().toString());
                } else {
                    callback.onTimeDataReceived(Collections.emptyList());
                    Log.d("TimeController", "onTimeResponse: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Time>> call, Throwable t) {
                Log.d("TimeController", "onTimeFailure: " + t.toString());
                callback.onTimeDataReceived(Collections.emptyList());
            }
        });
    }
}
