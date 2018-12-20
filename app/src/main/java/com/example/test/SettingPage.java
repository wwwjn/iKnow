package com.example.test;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SettingPage extends AppCompatActivity {
    ImageButton labelBtn,publishBtn,accountBtn,contactBtn;
    Button publicBtn,privateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);
        labelBtn= (ImageButton)findViewById(R.id.turntoLabelSetting);
        publishBtn= (ImageButton)findViewById(R.id.turntoPublishEvent);
        accountBtn= (ImageButton)findViewById(R.id.turntoAccountSetting);
        contactBtn=(ImageButton)findViewById(R.id.turntoContactUs);
        publicBtn= (Button)findViewById(R.id.PublicButton);
        privateBtn= (Button)findViewById(R.id.PrivateButton);


        //佳妮的发布消息页面
        /*
        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this,CreatActivity.class);
                startActivity(intent);
            };
        });
        */

        //佳妮的用户设置页面
        /*
        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(SettingActivity.this,AccountSetting.class);
                startActivity(intent2);
            };
        });
        */

        //点击“公共日历”按钮，跳转到"公共日历"页面
        publicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(SettingPage.this,PublicPage.class);
                startActivity(intent2);
                SettingPage.this.finish();
            };
        });

        //点击“我的日历”按钮，跳转到"我的日历"页面
        privateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(SettingPage.this,PrivatePage.class);
                startActivity(intent2);
                SettingPage.this.finish();
            };
        });

    }




}
