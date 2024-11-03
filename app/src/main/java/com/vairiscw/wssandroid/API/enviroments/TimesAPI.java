package com.vairiscw.wssandroid.API.enviroments;

import com.vairiscw.wssandroid.data.environment.Time;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TimesAPI {
    @GET("/environments/time")
    Call<List<Time>> getTimes();
}
