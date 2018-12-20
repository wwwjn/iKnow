package com.example.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class PrivatePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_page);
    }

    //点击"公共日历"按钮，进入“公共日历”页面
    public void gotoPublicPage(View view)
    {
        Intent intent = new Intent(PrivatePage.this, PublicPage.class);
        startActivity(intent);
        PrivatePage.this.finish();
    }

    //点击"软件设置"按钮，进入“软件设置”页面
    public void gotoSettingPage(View view)
    {
        Intent intent = new Intent(PrivatePage.this, SettingPage.class);
        startActivity(intent);
        PrivatePage.this.finish();
    }
}
