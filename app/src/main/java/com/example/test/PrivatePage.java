package com.example.test;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.util.*;


public class PrivatePage extends AppCompatActivity {
    private int DateButtonNum = 30;
    private Button[] DateButton = new Button[DateButtonNum];

    public void Init(){
        LinearLayout layout = findViewById(R.id.DateViewLayout);
        for(int i = 0; i < DateButtonNum; i++){
            DateButton[i] = new Button(this);
            DateButton[i].setText("一\r\n26");
            DateButton[i].setBackgroundColor(Color.WHITE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getPixelsFromDp(50), LinearLayout.LayoutParams.MATCH_PARENT);
            DateButton[i].setLayoutParams(params);
            layout.addView(DateButton[i]);
        }
    }

    private int getPixelsFromDp(int size){
        DisplayMetrics metrics =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return(size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_page);
        Init();
    }

    //点击"公共日历"按钮，进入“公共日历”页面
    public void gotoPublicPage(View view){
        Intent intent = new Intent(PrivatePage.this, PublicPage.class);
        startActivity(intent);
    }

    //点击"软件设置"按钮，进入“软件设置”页面
    public void gotoSettingPage(View view) {
        Intent intent = new Intent(PrivatePage.this, SettingPage.class);
        startActivity(intent);
    }
}
