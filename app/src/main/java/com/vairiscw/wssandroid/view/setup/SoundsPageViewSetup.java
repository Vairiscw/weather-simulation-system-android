package com.vairiscw.wssandroid.view.setup;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.vairiscw.wssandroid.API.enviroments.VoiceController;
import com.vairiscw.wssandroid.API.template.TemplateController;
import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.API.callbacks.VoiceCallback;
import com.vairiscw.wssandroid.data.environment.Voice;
import com.vairiscw.wssandroid.view.adapters.SmallListRecyclerAdapter;
import com.vairiscw.wssandroid.view.environment_page.SmallEnvironmentPage;

import java.util.ArrayList;
import java.util.List;

public class SoundsPageViewSetup {
    View mainView;
    ViewPager2 soundsRecyclerView;
    List<SmallEnvironmentPage> soundsList;
    int[] soundsIcons = {R.drawable.creature_icon, R.drawable.water_icon, R.drawable.birds_icon, R.drawable.fireplace_icon};
    List<Voice> voicesFromServer;
    SmallListRecyclerAdapter soundsRecyclerAdapter;
    TemplateController templateController;
    VoiceController voiceController;

    public SoundsPageViewSetup(View mainView, TemplateController templateController) {
        this.mainView = mainView;
        this.templateController = templateController;
        this.voiceController = new VoiceController();
        voiceController.getAllVoices(new VoiceCallback() {
            @Override
            public void onVoiceDataReceived(List<Voice> voiceList) {
                voicesFromServer = voiceList;

                soundsRecyclerSetup();
            }
        });
    }

    public void soundsRecyclerSetup() {
        soundsList = new ArrayList<>();
        for (int i = 0; i < voicesFromServer.size(); ++i) {
            soundsList.add(new SmallEnvironmentPage(soundsIcons[i], voicesFromServer.get(i).getDesignation()));
        }

        soundsRecyclerView = mainView.findViewById(R.id.soundsRecyclerView);
        soundsRecyclerAdapter = new SmallListRecyclerAdapter(soundsList, mainView.getContext());
        soundsRecyclerView.setAdapter(soundsRecyclerAdapter);


        soundsRecyclerView.setCurrentItem(Math.toIntExact(templateController.getCurrentVoiceId()) + voicesFromServer.size() *  2000 - 1);
        soundsRecyclerAdapter.setActivePosition(Math.toIntExact(templateController.getCurrentVoiceId()) + voicesFromServer.size() *  2000 - 1);
        soundsRecyclerView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                soundsRecyclerAdapter.setActivePosition(position);
                changeCurrentWeather(position % voicesFromServer.size());
            }
        });

        int padding = 50;
        soundsRecyclerView.setPadding(padding, 0, padding, 0);
        soundsRecyclerView.setClipToPadding(false);
        soundsRecyclerView.setClipChildren(false);
        soundsRecyclerView.setOffscreenPageLimit(1);

        soundsRecyclerView.setPageTransformer(new ViewPager2.PageTransformer()
        {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float absPos = Math.abs(position);

                if (position < -1) {
                    page.setAlpha(0f);
                } else if (position <= 1) {
                    page.setAlpha(1f);
                    page.setTranslationX(-position * page.getWidth());
                    float scaleFactor = 0.85f + (1 - absPos) * 0.15f;
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                } else {
                    page.setAlpha(0f);
                }
            }
        });

    }

    private void changeCurrentWeather(int position) {

        templateController.updateVoice(voicesFromServer.get(position));
    }
}
