package com.vairiscw.wssandroid.data.temperature;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TemperatureAPI {

    @GET("/temp")
    Call<List<TemperatureData>> getTemperatures();
}
