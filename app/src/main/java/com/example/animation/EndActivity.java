package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class EndActivity extends AppCompatActivity {

    MediaPlayer musicEnd;
    Animation btnAnimation;
    Button playBtn;
    private static int SPLASH_SCREEN=5000;

    private long pressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        musicEnd = MediaPlayer.create(this,R.raw.clap);
        musicEnd.start();

        btnAnimation= AnimationUtils.loadAnimation(this,R.anim.end_btn_animation);

        playBtn=findViewById(R.id.playBtn);

        playBtn.setAnimation(btnAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                musicEnd.stop();
            }
        },SPLASH_SCREEN);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EndActivity.this,
                        com.example.animation.GameActivity.class);

                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }



}


