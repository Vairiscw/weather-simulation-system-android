package com.vairiscw.wssandroid.API.enviroments;

import com.vairiscw.wssandroid.data.environment.Scene;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ScenesAPI {

    @GET("/environments/scene")
    Call<List<Scene>> getScenes();
}
