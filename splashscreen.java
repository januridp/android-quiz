package com.example.kuisfisika;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;



public class splashscreen extends Activity {
    private static final int SPLASH_TIME = 8000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(splashscreen.this, utama.class);
                startActivity(intent);
                splashscreen.this.finish();
            }

        }, SPLASH_TIME);
    }



}