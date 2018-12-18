package com.example.test;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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
                gotoPrivatePage();
            }
        },1000);


    }

    //点击"我的日历"按钮，进入“我的日历”页面
    public void gotoPrivatePage()
    {
        Intent intent = new Intent(InitialPage.this, PrivatePage.class);
        startActivity(intent);
    }


}

