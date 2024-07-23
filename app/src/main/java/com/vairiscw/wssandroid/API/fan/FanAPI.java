package com.vairiscw.wssandroid.API.fan;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FanAPI {

    @POST("/fan")
    Call<ResponseBody> postStatus(@Query("state") String status);

    @GET("/fan")
    Call<ResponseBody> getStatus();

}
