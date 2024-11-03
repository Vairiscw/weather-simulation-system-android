package com.vairiscw.wssandroid.view.setup;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager2.widget.ViewPager2;

import com.vairiscw.wssandroid.API.callbacks.SceneCallback;
import com.vairiscw.wssandroid.API.enviroments.ScenesController;
import com.vairiscw.wssandroid.API.template.TemplateController;
import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.data.environment.Scene;
import com.vairiscw.wssandroid.view.adapters.BigListRecyclerAdapter;
import com.vairiscw.wssandroid.view.environment_page.BigEnvironmentPage;

import java.util.ArrayList;
import java.util.List;

public class SceneMenuSetup {
    View mainView;

    int[] sceneImages = {R.drawable.stone_garden};
    List<Scene> scenesFromServer;
    List<BigEnvironmentPage> scenePagesList;
    Button sceneChoiceButton;
    ImageView sceneExitButton;
    ViewPager2 sceneRecyclerView;
    BigListRecyclerAdapter sceneRecyclerAdapter;
    TextView sceneUnderText;
    TextView TitleText;
    TemplateController templateController;
    ScenesController scenesController;

    public SceneMenuSetup(View v, TemplateController templateController) {
        this.templateController = templateController;
        scenesController = new ScenesController();
        this.mainView = v;

        scenesController.getAllScenes(new SceneCallback() {
            @Override
            public void onSceneDataReceived(List<Scene> sceneList) {
                scenesFromServer = sceneList;

                sceneChoiceSetup();
            }
        });
    }

    public void sceneChoiceSetup() {
        Dialog dialog = new Dialog(mainView.getContext());
        sceneChoiceButton = mainView.findViewById(R.id.sceneButton);
        sceneChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.choice_window_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);

                TitleText = dialog.findViewById(R.id.textView);
                TitleText.setText("Окружение");

                sceneExitButton = dialog.findViewById(R.id.videoExitButton);
                sceneUnderText = dialog.findViewById(R.id.titleTextView);

                sceneExitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                scenePagesList = new ArrayList<>();
                for (int i = 0; i < scenesFromServer.size(); ++i) {
                    scenePagesList.add(new BigEnvironmentPage(sceneImages[i]));
                }

                sceneRecyclerView = dialog.findViewById(R.id.windowedRecyclerView);

                sceneRecyclerAdapter = new BigListRecyclerAdapter(scenePagesList, v.getContext());
                sceneRecyclerView.setAdapter(sceneRecyclerAdapter);

                sceneRecyclerView.setCurrentItem(Math.toIntExact(templateController.getCurrentSceneId()) + scenesFromServer.size() *  2000 - 1);
                sceneRecyclerAdapter.setActivePosition(Math.toIntExact(templateController.getCurrentSceneId()) + scenesFromServer.size() *  2000 - 1);;
                sceneRecyclerView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        sceneRecyclerAdapter.setActivePosition(position);
                        sceneUnderText.setText(scenesFromServer.get(position % scenesFromServer.size()).getDesignation());

                        changeCurrentScene(position % scenesFromServer.size());
                    }
                });

                dialog.show();
            }
        });
    }

    private void changeCurrentScene(int position) {
        templateController.updateScene(scenesFromServer.get(position));
    }

}
