package com.vairiscw.wssandroid.data.template;

import com.vairiscw.wssandroid.data.environment.Scene;
import com.vairiscw.wssandroid.data.environment.Time;
import com.vairiscw.wssandroid.data.environment.Video;
import com.vairiscw.wssandroid.data.environment.Voice;
import com.vairiscw.wssandroid.data.environment.Weather;

public class Template {
    private Long id;
    private String author;
    private String name;

    private Scene scene;

    private Time time;

    private Video video;

    private Voice voice;

    private Weather weather;
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
