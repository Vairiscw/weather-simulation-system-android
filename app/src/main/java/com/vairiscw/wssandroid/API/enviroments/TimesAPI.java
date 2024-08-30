package com.vairiscw.wssandroid.API.enviroments;

import com.vairiscw.wssandroid.data.environment.Time;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TimesAPI {
    @GET("/components/time")
    Call<List<Time>> getTimes();
}
