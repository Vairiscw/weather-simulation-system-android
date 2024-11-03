package com.vairiscw.wssandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.vairiscw.wssandroid.API.template.TemplateController;
import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.API.callbacks.TemplateCallback;
import com.vairiscw.wssandroid.data.template.Template;
import com.vairiscw.wssandroid.view.setup.MenuSetup;
import com.vairiscw.wssandroid.view.setup.SceneMenuSetup;
import com.vairiscw.wssandroid.view.setup.SoundsPageViewSetup;
import com.vairiscw.wssandroid.view.setup.TimesButtonSetup;
import com.vairiscw.wssandroid.view.setup.VideoMenuSetup;
import com.vairiscw.wssandroid.view.setup.WeatherPageViewSetup;

public class MainActivity extends AppCompatActivity {
    //Параметры погоды
    WeatherPageViewSetup weatherSetup;

    //Параметры звука
    SoundsPageViewSetup soundsSetup;

    // Параметры время
    TimesButtonSetup timeSetup;

    TextView TitleText;
    //Параметры видео
    VideoMenuSetup videoSetup;
    //Параметры сцены
    SceneMenuSetup sceneSetup;

    //Меню
    MenuSetup menuSetup;

    //Интернет
    TemplateController templateController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        templateController = new TemplateController();

        templateController.getCurrentTemplate(new TemplateCallback() {
            @Override
            public void onTemplateDataReceived(Template template) {
                templateController.setTemplate(template);

                runAllSetups();
            }
        });
    }

    private void runAllSetups() {
        menuSetup = new MenuSetup(findViewById(android.R.id.content), templateController);
        menuSetup.menuSetup();

        weatherSetup = new WeatherPageViewSetup(findViewById(android.R.id.content), templateController);

        timeSetup = new TimesButtonSetup(findViewById(android.R.id.content), templateController);

        soundsSetup = new SoundsPageViewSetup(findViewById(android.R.id.content), templateController);

        videoSetup = new VideoMenuSetup(findViewById(android.R.id.content), templateController);

        sceneSetup = new SceneMenuSetup(findViewById(android.R.id.content), templateController);
    }



//    protected void postStatus() {
//        Call<ResponseBody> call = fanAPI.postStatus(windStatus);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccessful()) {
//                    windStatusTV.setText(windStatus);
//                    Log.d("TAG", windStatus);
//                    if (windStatus.equals("on")) windStatus = "off";
//                    else if (windStatus.equals("off")) windStatus = "on";
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
//                Log.d("TAG", throwable.getMessage());
//                finish();
//            }
//        });
//    }}



}
