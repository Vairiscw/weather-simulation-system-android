package com.vairiscw.wssandroid.data.times;

import com.vairiscw.wssandroid.data.scenes.Scenes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TimesAPI {
    @GET("/components/time")
    Call<List<Times>> getTimes();
}
