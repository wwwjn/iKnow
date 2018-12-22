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
    private int ShowNum = 0;

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
        for(int i = 20181225; i < 20181225+ShowNum; i++){
            View v = findViewById(i);
            ViewGroup vg = (ViewGroup) v.getParent();
            vg.removeView(v);
        }
        ShowNum = 0;
        for(int i = 0; i < 9; i++){
            cursor = mysql.Query(CurrentYear, CurrentMonth%12+1, (CurrentDate + CurrentDateId - 1)%31+1,2*i+6);
            int num = cursor.getCount();
            for(int j = 0; j < num; j++){
                cursor.moveToNext();
                addActivityButton(num,cursor,i,j);
            }
        }
    }

    private void addActivityButton(int n, Cursor ActivityCursor, int i, int j){
        final int ThisId = ActivityCursor.getInt(ActivityCursor.getColumnIndex("Id"));
        LinearLayout layout = findViewById(R.id.TimeView06 + i);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getPixelsFromDp(200/n), LinearLayout.LayoutParams.MATCH_PARENT);
        Button ActivityButton = new Button(this);
        ActivityButton.setId(20181225+ShowNum);
        ActivityButton.setText(ActivityCursor.getString(ActivityCursor.getColumnIndex("name")) + "\n时间:" +
                ActivityCursor.getString(ActivityCursor.getColumnIndex("startHour")) + ":" + ActivityCursor.getString(ActivityCursor.getColumnIndex("startMinute"))
                + "-" + ActivityCursor.getString(ActivityCursor.getColumnIndex("endHour")) + ":" + ActivityCursor.getString(ActivityCursor.getColumnIndex("endMinute")) +
                "    地点:" + ActivityCursor.getString(ActivityCursor.getColumnIndex("place")));
        ActivityButton.setTextSize(14-2*n);
        ActivityButton.setBackgroundResource(R.drawable.button_myactivity1 + j%2);
        ActivityButton.setLayoutParams(params);
        ActivityButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(PrivatePage.this, DetailPage.class);
                intent.putExtra("ActivityId", ThisId);
                startActivity(intent);
            }
        });
        layout.addView(ActivityButton);
        ShowNum += 1;
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
        mysql.Insert(1,2018,12,25,8,15,9,45,"这是活动1","主标签",
                "副标签","活动标签","清华大学","拒绝熬夜组",null,"圣诞聚餐1");
        mysql.Insert(2,2018,12,25,9,15,9,45,"这是活动2","主标签",
                "副标签","活动标签","清华大学","拒绝熬夜组",null,"圣诞聚餐2");
        mysql.Insert(3,2018,12,25,9,15,16,45,"这是活动3","主标签",
                "副标签","活动标签","清华大学","拒绝熬夜组",null,"圣诞聚餐3");
        mysql.Insert(4,2018,12,26,9,15,16,45,"这是活动3","主标签",
                "副标签","活动标签","清华大学","拒绝熬夜组",null,"圣诞聚餐4");
        mysql.Insert(5,2018,12,26,9,15,16,45,"这是活动3","主标签",
                "副标签","活动标签","清华大学","拒绝熬夜组",null,"圣诞聚餐5");
        mysql.Insert(6,2018,12,27,9,15,16,45,"这是活动3","主标签",
                "副标签","活动标签","清华大学","拒绝熬夜组",null,"圣诞聚餐6");*/
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
