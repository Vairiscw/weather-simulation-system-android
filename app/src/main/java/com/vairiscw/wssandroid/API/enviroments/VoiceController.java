package com.vairiscw.wssandroid.API.enviroments;

import android.util.Log;

import com.vairiscw.wssandroid.API.ConnectionInformation;
import com.vairiscw.wssandroid.API.callbacks.VoiceCallback;
import com.vairiscw.wssandroid.data.environment.Voice;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VoiceController {
    VoiceAPI api;
    ConnectionInformation information;
    List<Voice> allVoices;

    public VoiceController() {
        information = new ConnectionInformation();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(information.getServerUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(VoiceAPI.class);
    }

    public void getAllVoices(VoiceCallback callback) {
        Call<List<Voice>> call = api.getVoices();
        call.enqueue(new Callback<List<Voice>>() {
            @Override
            public void onResponse(Call<List<Voice>> call, Response<List<Voice>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onVoiceDataReceived(response.body());
                    Log.d("VoiceController", "onVoiceResponse: " + response.body().toString());
                } else {
                    callback.onVoiceDataReceived(Collections.emptyList());
                    Log.d("VoiceController", "onVoiceResponse: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Voice>> call, Throwable t) {
                Log.d("VoiceController", "onVoiceFailure: " + t.toString());
                callback.onVoiceDataReceived(Collections.emptyList());
            }
        });
    }
}
