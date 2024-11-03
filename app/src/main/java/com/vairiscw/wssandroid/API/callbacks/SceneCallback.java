package com.vairiscw.wssandroid.API.callbacks;

import com.vairiscw.wssandroid.data.environment.Scene;

import java.util.List;

public interface SceneCallback {
    void onSceneDataReceived(List<Scene> sceneList);
}
