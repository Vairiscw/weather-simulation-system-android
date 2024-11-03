package com.vairiscw.wssandroid.API.callbacks;

import com.vairiscw.wssandroid.data.environment.Time;

import java.util.List;

public interface TimeCallback {
    void onTimeDataReceived(List<Time> timeList);
}
