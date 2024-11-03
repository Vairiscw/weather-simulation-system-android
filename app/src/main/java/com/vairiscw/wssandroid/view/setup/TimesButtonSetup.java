package com.vairiscw.wssandroid.view.setup;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.vairiscw.wssandroid.API.enviroments.TimesController;
import com.vairiscw.wssandroid.API.template.TemplateController;
import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.API.callbacks.TimeCallback;
import com.vairiscw.wssandroid.data.environment.Time;

import java.util.List;

public class TimesButtonSetup {
    View mainView;
    //Параметры дня
    ImageView sunIcon;
    TextView sunText;
    List<Time> timeList;
    Button timeSunButton;
    //Параметры ночи
    ImageView nightIcon;
    TextView nightText;
    Button timeNightButton;
    TemplateController templateController;
    TimesController timesController;

    public TimesButtonSetup(View mainView, TemplateController templateController) {
        this.mainView = mainView;
        this.templateController = templateController;
        this.timesController = new TimesController();

        timesController.getAllTimes(new TimeCallback() {
            @Override
            public void onTimeDataReceived(List<Time> times) {
                timeList = times;
                Log.d("TimeButtonSetup", "onTimeDataReceived: " + timeList.size());

                timeButtonSetup();
            }
        });
    }

    public void timeButtonSetup() {
        sunIcon = mainView.findViewById(R.id.timeSunIcon);
        sunText =  mainView.findViewById(R.id.timeSunText);
        sunIcon.setImageResource(R.drawable.sun_icon);
        sunIcon.setColorFilter(ContextCompat.getColor(mainView.getContext().getApplicationContext(), R.color.disable_icon_color));
        timeSunButton = mainView.findViewById(R.id.timeSunButton);
        timeSunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSunActive();
            }
        });

        nightIcon =  mainView.findViewById(R.id.timeNightIcon);
        nightText =  mainView.findViewById(R.id.timeNightText);
        nightIcon.setImageResource(R.drawable.night_icon);
        nightIcon.setColorFilter(ContextCompat.getColor(mainView.getContext().getApplicationContext(), R.color.disable_icon_color));
        timeNightButton =  mainView.findViewById(R.id.timeNightButton);
        timeNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNightActive();
            }
        });

        if (templateController.getCurrentTimeId() == 1) {
            setSunActive();
        } else if (templateController.getCurrentTimeId() == 2) {
            setNightActive();
        }
    }

    private void setSunActive() {
        sunIcon.setColorFilter(ContextCompat.getColor(mainView.getContext().getApplicationContext(), R.color.active_sun_icon_color));
        sunText.setTextColor(ContextCompat.getColor(mainView.getContext().getApplicationContext(), R.color.active_sun_icon_color));

        nightIcon.setColorFilter(ContextCompat.getColor(mainView.getContext().getApplicationContext(), R.color.disable_icon_color));
        nightText.setTextColor(ContextCompat.getColor(mainView.getContext().getApplicationContext(), R.color.disable_icon_color));

        templateController.updateTime(timeList.get(0));
    }

    private void setNightActive() {
        nightIcon.clearColorFilter();
        nightIcon.setColorFilter(ContextCompat.getColor(mainView.getContext().getApplicationContext(), R.color.text_color));
        nightText.setTextColor(ContextCompat.getColor(mainView.getContext().getApplicationContext(), R.color.text_color));
        sunIcon.clearColorFilter();
        sunIcon.setColorFilter(ContextCompat.getColor(mainView.getContext().getApplicationContext(), R.color.disable_icon_color));
        sunText.setTextColor(ContextCompat.getColor(mainView.getContext().getApplicationContext(), R.color.disable_icon_color));

        templateController.updateTime(timeList.get(1));
    }
}
