package com.app.instancedownload;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class w_SplashScreen extends AppCompatActivity {
    final static int SPLASH_TIMER=2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_activity_splash_screen);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(w_SplashScreen.this, w_MainActivity.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_TIMER);
    }
}