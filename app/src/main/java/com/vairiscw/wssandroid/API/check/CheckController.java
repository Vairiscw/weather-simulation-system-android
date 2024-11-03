package com.vairiscw.wssandroid.API.check;

import android.util.Log;

import com.vairiscw.wssandroid.API.ConnectionInformation;
import com.vairiscw.wssandroid.API.callbacks.CheckCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckController {
    ConnectionInformation information;
    CheckAPI api;

    public CheckController() {
        information = new ConnectionInformation();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(information.getServerUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(CheckAPI.class);
    }

    public void tryConnect(CheckCallback callback) {
        Call<ResponseBody> call = api.checkingConnection();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.d("StartActivity", "Successful");
                    callback.onConnectionChecked(true);
                }
                else {
                    Log.d("StartActivity", "What");
                    callback.onConnectionChecked(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d("TAG", throwable.getMessage());Log.d("TAG", call.request().toString());
                callback.onConnectionChecked(false);
            }
        });
    }
}
