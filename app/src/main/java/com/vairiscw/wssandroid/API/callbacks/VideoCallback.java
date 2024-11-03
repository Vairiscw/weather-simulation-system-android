package com.vairiscw.wssandroid.API.callbacks;

import com.vairiscw.wssandroid.data.environment.Video;

import java.util.List;

public interface VideoCallback {

    void onVideoDataReceived(List<Video> videoList);
}
