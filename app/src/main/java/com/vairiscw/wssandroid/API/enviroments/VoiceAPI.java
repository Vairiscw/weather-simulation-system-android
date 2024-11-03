package com.vairiscw.wssandroid.API.enviroments;

import com.vairiscw.wssandroid.data.environment.Time;
import com.vairiscw.wssandroid.data.environment.Voice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VoiceAPI {
    @GET("/environments/voice")
    Call<List<Voice>> getVoices();
}
