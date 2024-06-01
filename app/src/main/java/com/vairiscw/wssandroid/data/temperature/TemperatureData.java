package com.vairiscw.wssandroid.data.temperature;

import java.time.LocalTime;


public class TemperatureData {
    private Long id;
    private Integer temperature;
    private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer tempSetting) {
        temperature = tempSetting;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TemperatureData(Long id, Integer temperature, String time) {
        this.id = id;
        this.temperature = temperature;
        this.time = time;
    }
}
