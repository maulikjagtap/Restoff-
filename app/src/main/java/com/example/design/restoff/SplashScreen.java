package com.example.design.restoff;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    private Handler handler;
    private Runnable runnable;
    private int time=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                Intent login=new Intent(SplashScreen.this,Basicintro.class);
                startActivity(login);
                finish();

            }
        };
        handler.postDelayed(runnable,time);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
