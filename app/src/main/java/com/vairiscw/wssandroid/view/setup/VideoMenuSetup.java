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

import com.vairiscw.wssandroid.API.enviroments.VideoController;
import com.vairiscw.wssandroid.API.template.TemplateController;
import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.API.callbacks.VideoCallback;
import com.vairiscw.wssandroid.data.environment.Video;
import com.vairiscw.wssandroid.view.adapters.BigListRecyclerAdapter;
import com.vairiscw.wssandroid.view.environment_page.BigEnvironmentPage;

import java.util.ArrayList;
import java.util.List;

public class VideoMenuSetup {
    View mainView;

    int[] videoImages = {R.drawable.castle};
    List<Video> videosFromServer;
    List<BigEnvironmentPage> videoList;
    Button videoChoiceButton;
    ImageView videoExitButton;
    ViewPager2 videoRecyclerView;
    BigListRecyclerAdapter videoRecyclerAdapter;
    TextView videoUnderText;
    TextView TitleText;

    TemplateController templateController;
    VideoController videoController;

    public VideoMenuSetup(View v, TemplateController templateController) {
        this.templateController = templateController;
        videoController = new VideoController();
        this.mainView = v;

        videoController.getAllVideos(new VideoCallback() {
            @Override
            public void onVideoDataReceived(List<Video> videoList) {
                videosFromServer = videoList;

                videoChoiceSetup();
            }
        });
    }

    public void videoChoiceSetup() {
        videoChoiceButton = mainView.findViewById(R.id.videoButton);
        Dialog dialog = new Dialog(mainView.getContext());

        videoChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.choice_window_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);

                TitleText = dialog.findViewById(R.id.textView);

                TitleText.setText("Видео");

                videoExitButton = dialog.findViewById(R.id.videoExitButton);
                videoUnderText = dialog.findViewById(R.id.titleTextView);

                videoExitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                videoList = new ArrayList<>();
                for (int i = 0; i < videosFromServer.size(); ++i) {
                    videoList.add(new BigEnvironmentPage(videoImages[i]));
                }

                videoRecyclerView = dialog.findViewById(R.id.windowedRecyclerView);

                videoRecyclerAdapter = new BigListRecyclerAdapter(videoList, v.getContext());
                videoRecyclerView.setAdapter(videoRecyclerAdapter);

                videoRecyclerView.setCurrentItem(Math.toIntExact(templateController.getCurrentVideoId()) + videosFromServer.size() *  2000 - 1);
                videoRecyclerAdapter.setActivePosition(Math.toIntExact(templateController.getCurrentVideoId()) + videosFromServer.size() *  2000 - 1);
                videoRecyclerView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        videoRecyclerAdapter.setActivePosition(position);
                        videoUnderText.setText(videosFromServer.get(position % videosFromServer.size()).getDesignation());

                        changeCurrentVideo(position % videosFromServer.size());
                    }
                });

                dialog.show();
            }
        });
    }

    private void changeCurrentVideo(int position) {
        templateController.updateVideo(videosFromServer.get(position));
    }
}
