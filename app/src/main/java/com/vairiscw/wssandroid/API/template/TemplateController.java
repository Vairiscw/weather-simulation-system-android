package com.vairiscw.wssandroid.API.template;

import android.util.Log;

import com.vairiscw.wssandroid.API.ConnectionInformation;
import com.vairiscw.wssandroid.API.callbacks.TemplateCallback;
import com.vairiscw.wssandroid.data.environment.Scene;
import com.vairiscw.wssandroid.data.environment.Time;
import com.vairiscw.wssandroid.data.environment.Video;
import com.vairiscw.wssandroid.data.environment.Voice;
import com.vairiscw.wssandroid.data.environment.Weather;
import com.vairiscw.wssandroid.data.template.Template;

import retrofit2.Call;
import retrofit2.Callback;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TemplateController {
    ConnectionInformation information;
    Template template;
    TemplateAPI api;

    public TemplateController() {
        information = new ConnectionInformation();
        setup();

    }

    private void setup() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(information.getServerUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(TemplateAPI.class);

    }

    public void updateTime(Time time) {
        template.setTime(time);
        postCurrentTemplate();
    }

    public void updateScene(Scene scene) {
        template.setScene(scene);
        postCurrentTemplate();
    }

    public void updateVideo(Video video) {
        template.setVideo(video);
        postCurrentTemplate();
    }

    public void updateVoice(Voice voice) {
        template.setVoice(voice);
        postCurrentTemplate();
    }

    public void updateWeather(Weather weather) {
        template.setWeather(weather);
        postCurrentTemplate();
    }

    public void getCurrentTemplate(TemplateCallback callback) {
        Call<Template> call = api.getTemplateById(1);
        call.enqueue(new Callback<Template>() {
            @Override
            public void onResponse(Call<Template> call, Response<Template> response) {
                if (response.isSuccessful()){
                    Log.d("TemplateController", "Successful getting template");
                    callback.onTemplateDataReceived(response.body());
                } else {
                    Log.d("TemplateController", "Something went wrong");
                    callback.onTemplateDataReceived(null);
                }
            }
            @Override
            public void onFailure(Call<Template> call, Throwable throwable) {
                Log.d("TemplateController", throwable.getMessage());
                callback.onTemplateDataReceived(null);
            }
        });

    }

    private void postCurrentTemplate() {
        Call<ResponseBody> call = api.updateCurrentTemplate(template);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.d("TemplateController", "Successful update");
                } else {
                    Log.d("TemplateController", "Something went wrong while POST template");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d("TemplateController", throwable.getMessage());
            }
        });
    }

    public void saveTemplate() {
        Template saving = template;
        saving.setAuthor("android");
        Call<ResponseBody> call = api.postTemplate(saving);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.d("TemplateController", "Successful save");
                } else {
                    Log.d("TemplateController", "Something went wrong while save");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d("TemplateController", throwable.getMessage());
            }
        });
    }

    public Long getCurrentWeatherId() {
        return template.getWeather().getId();
    }
    public Long getCurrentVoiceId() {
        return template.getVoice().getId();
    }
    public Long getCurrentTimeId() {
        return template.getTime().getId();
    }
    public Long getCurrentVideoId() {
        return template.getVideo().getId();
    }
    public Long getCurrentSceneId() {
        return template.getScene().getId();
    }
    public void setTemplate(Template template) {
        this.template = template;
    }
}
