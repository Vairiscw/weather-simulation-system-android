package com.vairiscw.wssandroid.API.enviroments;

import android.util.Log;

import com.vairiscw.wssandroid.API.ConnectionInformation;
import com.vairiscw.wssandroid.API.callbacks.SceneCallback;
import com.vairiscw.wssandroid.data.environment.Scene;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScenesController {
    ScenesAPI api;
    ConnectionInformation information;
    List<Scene> allScenes;

    public ScenesController() {
        information = new ConnectionInformation();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(information.getServerUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(ScenesAPI.class);
    }

    public void getAllScenes(SceneCallback callback) {
        Call<List<Scene>> call = api.getScenes();
        call.enqueue(new Callback<List<Scene>>() {
            @Override
            public void onResponse(Call<List<Scene>> call, Response<List<Scene>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSceneDataReceived(response.body());
                    Log.d("SceneController", "onSceneResponse: " + response.body().toString());
                } else {
                    callback.onSceneDataReceived(Collections.emptyList());
                    Log.d("SceneController", "onSceneResponse: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Scene>> call, Throwable t) {
                Log.d("SceneController", "onSceneFailure: " + t.toString());
                callback.onSceneDataReceived(Collections.emptyList());
            }
        });
    }
}
