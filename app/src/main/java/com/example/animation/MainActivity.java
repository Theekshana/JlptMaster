package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MediaPlayer music;
    private static int SPLASH_SCREEN=5000;

    Animation topAnimation,bottomAnimation;
    TextView appname;
    TextView appname1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        music = MediaPlayer.create(this,R.raw.music);
        music.start();

        topAnimation= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        appname=findViewById(R.id.appName);
        appname1=findViewById(R.id.appName1);

        appname.setAnimation(topAnimation);
        appname1.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
                music.stop();
            }
        },SPLASH_SCREEN);


    }

    @Override
    public void onBackPressed(){
        System.gc();
        System.exit(0);
    }




}

