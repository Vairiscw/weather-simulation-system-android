package com.vairiscw.wssandroid.API.check;

import retrofit2.Call;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface CheckAPI {
    @GET("/templates")
    Call<ResponseBody> checkingConnection();
}
