package com.vairiscw.wssandroid.API.enviroments;

import android.util.Log;

import com.vairiscw.wssandroid.API.ConnectionInformation;
import com.vairiscw.wssandroid.API.callbacks.VideoCallback;
import com.vairiscw.wssandroid.data.environment.Video;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoController {
    VideoAPI api;
    ConnectionInformation information;
    List<Video> allVideos;

    public VideoController() {
        information = new ConnectionInformation();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(information.getServerUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(VideoAPI.class);
    }

    public void getAllVideos(VideoCallback callback) {
        Call<List<Video>> call = api.getVideos();
        call.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onVideoDataReceived(response.body());
                    Log.d("VideoController", "onVideoResponse: " + response.body().toString());
                } else {
                    callback.onVideoDataReceived(Collections.emptyList());
                    Log.d("VideoController", "onVideoResponse: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                Log.d("VideoController", "onVideoFailure: " + t.toString());
                callback.onVideoDataReceived(Collections.emptyList());
            }
        });
    }
}
