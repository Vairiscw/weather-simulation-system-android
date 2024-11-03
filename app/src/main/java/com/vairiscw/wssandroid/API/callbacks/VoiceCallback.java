package com.vairiscw.wssandroid.API.callbacks;

import com.vairiscw.wssandroid.data.environment.Voice;

import java.util.List;

public interface VoiceCallback {
    void onVoiceDataReceived(List<Voice> voiceList);
}
