package com.vairiscw.wssandroid.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.vairiscw.wssandroid.R;
import com.vairiscw.wssandroid.adapters.BigListRecyclerAdapter;
import com.vairiscw.wssandroid.adapters.SmallListRecyclerAdapter;
import com.vairiscw.wssandroid.config.RetrofitClient;
import com.vairiscw.wssandroid.API.fan.FanAPI;
import com.vairiscw.wssandroid.API.enviroments.ScenesAPI;
import com.vairiscw.wssandroid.API.enviroments.TimesAPI;
import com.vairiscw.wssandroid.view.environment_page.BigEnvironmentPage;
import com.vairiscw.wssandroid.view.environment_page.SmallEnvironmentPage;
import com.vairiscw.wssandroid.view.environment_page.NoPaddingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    //Параметры погоды
    ViewPager2 weatherRecyclerView;
    List<SmallEnvironmentPage> weatherList;
    int[] weatherIcons = {R.drawable.sun_icon, R.drawable.clouds_icon, R.drawable.rain_icon};
    String[] weatherTitles = {"Ясно", "Пасмурно", "Дождь"};
    int weatherListSize = 3;
    SmallListRecyclerAdapter weatherCoverAdapter;

    //Параметры звука
    ViewPager2 soundsRecyclerView;
    List<SmallEnvironmentPage> soundsList;
    int[] soundsIcons = {R.drawable.creature_icon, R.drawable.water_icon, R.drawable.birds_icon, R.drawable.fireplace_icon};
    String[] soundsTitles = {"Сверчки", "Вода", "Птицы", "Камин"};
    int soundsListSize = 4;
    SmallListRecyclerAdapter soundsRecyclerAdapter;
    //Параметры дня
    ImageView sunIcon;
    TextView sunText;
    Button timeSunButton;
    //Параметры ночи
    ImageView nightIcon;
    TextView nightText;
    Button timeNightButton;

    TextView TitleText;
    //Параметры видео
    int[] videoImages = {R.drawable.castle};
    String[] videoTitles = {"Замок"};
    int videoListSize = 1;
    List<BigEnvironmentPage> videoList;
    Button videoChoiceButton;
    ImageView videoExitButton;
    ViewPager2 videoRecyclerView;
    BigListRecyclerAdapter videoRecyclerAdapter;
    TextView videoUnderText;
    //Параметры сцены
    int[] sceneImages = {R.drawable.stone_garden};
    String[] sceneTitles = {"Сад камней"};
    int sceneListSize = 1;
    List<BigEnvironmentPage> sceneList;
    Button sceneChoiceButton;
    ImageView sceneExitButton;
    ViewPager2 sceneRecyclerView;
    BigListRecyclerAdapter sceneRecyclerAdapter;
    TextView sceneUnderText;

    //Меню
    ImageView template_button;

    //Интернет
    private final String serverUrl = "http://192.168.27.134:9898";
    FanAPI fanAPI;
    ScenesAPI scenesAPI;
    TimesAPI timesAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuSetup();
        weatherRecycleSetup();
        timeButtonSetup();
        soundsRecyclerSetup();
        videoChoiceSetup();
        sceneChoiceSetup();

        Retrofit retrofit = RetrofitClient.getClient(serverUrl);
        fanAPI = retrofit.create(FanAPI.class);
        scenesAPI = retrofit.create(ScenesAPI.class);
        timesAPI = retrofit.create(TimesAPI.class);
    }

    private void menuSetup() {
        template_button = findViewById(R.id.templates_menu_button);
        template_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.menu_tamplates);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.template_save) {

                    return true;
                }
                else if (id == R.id.show_templates) {

                    return true;
                }
                else if (id ==  R.id.template_delete) {

                    return true;
                }
                    else
                        return false;

            }
        });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();
    }

//    protected void postStatus() {
//
//        Call<ResponseBody> call = fanAPI.postStatus(windStatus);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccessful()) {
//                    windStatusTV.setText(windStatus);
//                    Log.d("TAG", windStatus);
//                    if (windStatus.equals("on")) windStatus = "off";
//                    else if (windStatus.equals("off")) windStatus = "on";
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
//                Log.d("TAG", throwable.getMessage());
//                finish();
//            }
//        });
//    }}

    protected void weatherRecycleSetup() {
        weatherList = new ArrayList<>();
        for (int i = 0; i < weatherListSize; ++i) {
            weatherList.add(new SmallEnvironmentPage(weatherIcons[i], weatherTitles[i]));
        }

        weatherRecyclerView = findViewById(R.id.weatherRecyclerView);
        weatherCoverAdapter = new SmallListRecyclerAdapter(weatherList, this);
        weatherRecyclerView.setAdapter(weatherCoverAdapter);

        weatherRecyclerView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                weatherCoverAdapter.setActivePosition(position);
            }
        });

        int padding = 50;
        weatherRecyclerView.setPadding(padding, 0, padding, 0);
        weatherRecyclerView.setClipToPadding(false);
        weatherRecyclerView.setClipChildren(false);
        weatherRecyclerView.setOffscreenPageLimit(1);

        weatherRecyclerView.setPageTransformer(new ViewPager2.PageTransformer()
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

    protected void timeButtonSetup() {
        sunIcon = findViewById(R.id.timeSunIcon);
        sunText = findViewById(R.id.timeSunText);
        sunIcon.setImageResource(R.drawable.sun_icon);
        sunIcon.setColorFilter(ContextCompat.getColor(this.getApplicationContext(), R.color.disable_icon_color));
        timeSunButton = findViewById(R.id.timeSunButton);
        timeSunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sunIcon.clearColorFilter();
                sunIcon.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.active_sun_icon_color));
                sunText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.active_sun_icon_color));
                //nightIcon.clearColorFilter();
                nightIcon.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.disable_icon_color));
                nightText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.disable_icon_color));
            }
        });

        nightIcon = findViewById(R.id.timeNightIcon);
        nightText = findViewById(R.id.timeNightText);
        nightIcon.setImageResource(R.drawable.night_icon);
        nightIcon.setColorFilter(ContextCompat.getColor(this.getApplicationContext(), R.color.disable_icon_color));
        timeNightButton = findViewById(R.id.timeNightButton);
        timeNightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nightIcon.clearColorFilter();
                nightIcon.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.text_color));
                nightText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.text_color));
                //sunIcon.clearColorFilter();
                sunIcon.setColorFilter(ContextCompat.getColor(v.getContext(), R.color.disable_icon_color));
                sunText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.disable_icon_color));
            }
        });
    }

    protected void soundsRecyclerSetup() {
        soundsList = new ArrayList<>();
        for (int i = 0; i < soundsListSize; ++i) {
            soundsList.add(new SmallEnvironmentPage(soundsIcons[i], soundsTitles[i]));
        }

        soundsRecyclerView = findViewById(R.id.soundsRecyclerView);
        soundsRecyclerAdapter = new SmallListRecyclerAdapter(soundsList, this);
        soundsRecyclerView.setAdapter(soundsRecyclerAdapter);

        soundsRecyclerView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                soundsRecyclerAdapter.setActivePosition(position);
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

    protected void videoChoiceSetup() {
        videoChoiceButton = findViewById(R.id.videoButton);
        Dialog dialog = new Dialog(MainActivity.this);

        videoChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.choice_window_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                //dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
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
                for (int i = 0; i < videoListSize; ++i) {
                    videoList.add(new BigEnvironmentPage(videoImages[i]));
                }

                videoRecyclerView = dialog.findViewById(R.id.windowedRecyclerView);

                videoRecyclerAdapter = new BigListRecyclerAdapter(videoList, v.getContext());
                videoRecyclerView.setAdapter(videoRecyclerAdapter);

                videoRecyclerView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        videoRecyclerAdapter.setActivePosition(position);
                    }
                });


//                videoRecyclerView.setPageTransformer(new ViewPager2.PageTransformer()
//                {
//                    @Override
//                    public void transformPage(@NonNull View page, float position) {
//                        float absPos = Math.abs(position);
//
//                        if (position < -1) {
//                            page.setAlpha(0f);
//                        } else if (position <= 1) {
//                            page.setAlpha(1f);
//                            page.setTranslationX(-position * page.getWidth());
//                            float scaleFactor = 0.85f + (1 - absPos) * 0.15f;
//                            page.setScaleX(scaleFactor);
//                            page.setScaleY(scaleFactor);
//                        } else {
//                            page.setAlpha(0f);
//                        }
//                    }
//                });

                dialog.show();
            }
        });
    }

    protected void sceneChoiceSetup() {
        sceneChoiceButton = findViewById(R.id.sceneButton);
        Dialog dialog = new Dialog(MainActivity.this);

        sceneChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.choice_window_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                //dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                TitleText = dialog.findViewById(R.id.textView);
                TitleText.setText("Видео");
                sceneExitButton = dialog.findViewById(R.id.videoExitButton);
                sceneUnderText = dialog.findViewById(R.id.titleTextView);

                sceneExitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                sceneList = new ArrayList<>();
                for (int i = 0; i < sceneListSize; ++i) {
                    sceneList.add(new BigEnvironmentPage(sceneImages[i]));
                }

                sceneRecyclerView = dialog.findViewById(R.id.windowedRecyclerView);

                sceneRecyclerAdapter = new BigListRecyclerAdapter(sceneList, v.getContext());
                sceneRecyclerView.setAdapter(sceneRecyclerAdapter);


                sceneRecyclerView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        sceneRecyclerAdapter.setActivePosition(position);
                    }
                });

                dialog.show();
            }
        });
    }
}
