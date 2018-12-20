package com.example.test;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class InitialPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_page);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                File file = new File(Environment.getExternalStorageDirectory(),"Username.txt");
                if (file.exists())
                    gotoPrivatePage();
                else
                    gotoLoginPage();
            }
        },3000);
    }

    public void gotoPrivatePage() {
        Intent intent = new Intent(InitialPage.this, PrivatePage.class);
        startActivity(intent);
    }

    public void gotoLoginPage(){
        Intent intent = new Intent(InitialPage.this, LoginPage.class);
        startActivity(intent);
    }
}