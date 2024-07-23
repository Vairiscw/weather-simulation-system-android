package com.vairiscw.wssandroid.data.scenes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ScenesAPI {

    @GET("/components/scene")
    Call<List<Scenes>> getScenes();
}
