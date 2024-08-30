package com.vairiscw.wssandroid.API.template;

import com.vairiscw.wssandroid.data.template.Template;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TemplateAPI {
    @POST("/templates")
    Call<ResponseBody> postTemplate(@Query("template")Template template);

    @GET("/templates")
    Call<List<Template>> getAllTemplates();

    @GET("/templates/{id}")
    Call<Template> getTemplateById(@Path("id") int id);
}
