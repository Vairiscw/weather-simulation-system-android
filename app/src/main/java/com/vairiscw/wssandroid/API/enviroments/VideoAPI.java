package com.vairiscw.wssandroid.API.enviroments;

import com.vairiscw.wssandroid.data.environment.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VideoAPI {
    @GET("/components/video")
    Call<List<Video>> getVideos();
}
