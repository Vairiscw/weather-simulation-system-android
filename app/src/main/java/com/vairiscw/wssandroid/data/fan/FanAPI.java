package com.vairiscw.wssandroid.data.fan;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FanAPI {

    @POST("/relay")
    Call<ResponseBody> postStatus(@Query("state") String status);

    @GET("/relay/state")
    Call<ResponseBody> getStatus();

}
