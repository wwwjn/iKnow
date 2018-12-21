package com.example.test;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import android.util.*;
import java.util.*;

public class PrivatePage extends AppCompatActivity {
    private int DateButtonNum = 20;
    private Button[] DateButton = new Button[DateButtonNum];
    private int CurrentDateId = 0;
    private int CurrentYear, CurrentMonth, CurrentDate;
    private MySql mysql;
    private Cursor cursor;

    private void Init(){
        LinearLayout layout = findViewById(R.id.DateViewLayout);
        Calendar Current = Calendar.getInstance();
        Current.setTimeInMillis(System.currentTimeMillis());
        CurrentYear = Current.get(Calendar.YEAR);
        CurrentMonth = Current.get(Calendar.MONTH);
        CurrentDate = Current.get(Calendar.DATE);
        int CurrentWeekday = Current.get(Calendar.DAY_OF_WEEK);
        String[] Week = { "日\n", "一\n", "二\n", "三\n", "四\n", "五\n", "六\n"};
        for(int i = 0; i < DateButtonNum; i++){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getPixelsFromDp(50), LinearLayout.LayoutParams.MATCH_PARENT);
            DateButton[i] = new Button(PrivatePage.this);
            if(i == CurrentDateId)
                DateButton[i].setBackgroundColor(Color.parseColor("#64c6a121"));
            else
                DateButton[i].setBackgroundColor(Color.WHITE);
            DateButton[i].setText(Week[(CurrentWeekday - 1 + i)%7] + String.valueOf((CurrentDate + i - 1)%31 + 1));
            DateButton[i].setId(i);
            DateButton[i].setLayoutParams(params);
            DateButton[i].setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    DateButton[CurrentDateId].setBackgroundColor(Color.WHITE);
                    CurrentDateId = v.getId();
                    DateButton[CurrentDateId].setBackgroundColor(Color.parseColor("#64c6a121"));
                    ShowActivity();
                }
            });
            layout.addView(DateButton[i]);
        }
    }

    private void ShowActivity(){
        mysql = new MySql(this);
        for(int i = 0; i < 9; i++){
            cursor = mysql.Query(CurrentYear, CurrentMonth+1, CurrentDate + CurrentDateId,2*i+6);
            if(cursor.getCount() > 0){
                LinearLayout layout = findViewById(R.id.TimeView06 + i);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                Button ActivityButton = new Button(this);
                cursor.moveToFirst();
                ActivityButton.setText(cursor.getString(cursor.getColumnIndex("name"))+"\n时间:"+
                        cursor.getString(cursor.getColumnIndex("startHour"))+":"+cursor.getString(cursor.getColumnIndex("startMinute"))
                +"-"+cursor.getString(cursor.getColumnIndex("endHour"))+":"+cursor.getString(cursor.getColumnIndex("endMinute"))+
                "    地点:"+cursor.getString(cursor.getColumnIndex("place")));
                ActivityButton.setTextSize(12);
                ActivityButton.setBackgroundResource(R.drawable.button_register);
                ActivityButton.setLayoutParams(params);
                layout.addView(ActivityButton);
            }
            //while(cursor.moveToNext())
                //Log.i("Connection", String.valueOf(i) + cursor.getString(cursor.getColumnIndex("host")));
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

        /*mysql = new MySql(this);
        cursor = mysql.Query();
        while(cursor.moveToNext())
            Log.i("Connection", cursor.getString(cursor.getColumnIndex("place") ));*/
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
