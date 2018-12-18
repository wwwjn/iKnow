package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


public class PublicPage extends AppCompatActivity  {

    Button browser;
    Button[] dateBtns = new Button[14];
    static int[] date={2018,11,11};

    Activity activity1 = new Activity(1,"8:00","10:00","智能车大赛真好玩啊哈哈哈哈哈哈哈啊哈哈","科创","六教6A214","汽车系科协");
    Activity activity2 = new Activity(2,"0:00","24:00","编程序真TM好玩啊哈水电费的说法呢发送到哈","计算机","没啥地点","拒绝熬夜组");
    Activity activity3 = new Activity(3,"16:00","20:00","生命学院学生节欢迎大家一起来看很好看的","社工","大礼堂","生命学院学生会");
    Activity activity4 = new Activity(4,"12:00","20:00","赶紧去学习吧哈哈哈哈哈哈哈哈哈哈哈哈","学习","#10 618","我");
    Activity[] Day1 = {activity1,activity2,activity4};
    Activity[] Day2 = {activity1,activity3,activity4};
    Activity[] Day3 = {activity2,activity2,activity4};
    Activity[] Day4 = {activity1,activity2,activity3};
    Activity[] Day5 = {activity1,activity2,activity3,activity4};
    Activity[] Day6 = {activity2,activity4};
    Activity[] Day7 = {activity1,activity2};
    Activity[][] activities = {Day1,Day2,Day3,Day4,Day5,Day6,Day7,Day1,Day2,Day3,Day4,Day5,Day6,Day7};
    private LinearLayout dateColumn;
    private LinearLayout publicActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_page);
        browser = findViewById(R.id.BrowserButton);
        browser.setOnClickListener(pageChangeListener);
        dateColumn = findViewById(R.id.dateColumn);
        initDateColumn(0);
        publicActivity = findViewById(R.id.publicActivity);
        initPublicActivity(activities[0]);

    }
    private void initDateColumn(int a){
        LinearLayout dateCase = dateColumn;
        int Size = 14;
        String item = "";
        int day = (date[2]+2*date[1]+3*(date[1]+1)/5+date[0]+date[0]/4-date[0]/100+date[0]/400)%7;
        for(int i = 0; i < Size; i++){
            switch((day+i)%7) {
                case 0: item = "一\n\n"; break;
                case 1: item = "二\n\n"; break;
                case 2: item = "三\n\n"; break;
                case 3: item = "四\n\n"; break;
                case 4: item = "五\n\n"; break;
                case 5: item = "六\n\n"; break;
                case 6: item = "日\n\n"; break;
            }
            item += String.valueOf(date[2]+i);
            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();

            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(width/7, LinearLayout.LayoutParams.MATCH_PARENT);
            itemParams.setMargins(0, 5, 0, 5);
            dateBtns[i] = (Button) LayoutInflater.from(this).inflate(R.layout.date_button, null);
            dateBtns[i].setText(item);
            dateBtns[i].setTag(item);
            dateBtns[i].setId(i);
            dateBtns[i].setLayoutParams(itemParams);
            dateBtns[i].setOnClickListener(dateChangeListener);
            dateBtns[a].setBackgroundColor(android.graphics.Color.rgb(237,189,101));
            dateCase.addView(dateBtns[i]);
            item = "";
        }
    }
    private void initPublicActivity(Activity[] dailyActivity){
        int num = dailyActivity.length;//当天事件数目
        LinearLayout activityList =  publicActivity;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // 每行的水平LinearLayout
        layoutParams.setMargins(0, 0, 0, 0);
        String circleButtonText = "";
        String barButtonText = "";
        for(int i = 0; i < num; i++){
            circleButtonText = dailyActivity[i].getStartTime() + "\n————\n" + dailyActivity[i].getEndTime();
            barButtonText = dailyActivity[i].getIntroduction().substring(0,14) + "...|" + dailyActivity[i].getClassification() + "\n\n" + dailyActivity[i].getHost() + "\t\t" + dailyActivity[i].getPlace();

            LinearLayout.LayoutParams circleButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            circleButtonParams.setMargins(0, 0, 0, 0);
            circleButtonParams.weight = 5;
            LinearLayout.LayoutParams barButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            barButtonParams.setMargins(0, 0, 0, 0);
            barButtonParams.weight = 1;
            Button circleBtn = (Button) LayoutInflater.from(this).inflate(R.layout.circle_button, null);
            Button barBtn = (Button) LayoutInflater.from(this).inflate(R.layout.bar_button, null);
            circleBtn.setText(circleButtonText);
            barBtn.setText(barButtonText);
            circleBtn.setLayoutParams(circleButtonParams);
            barBtn.setLayoutParams(barButtonParams);
            if(dailyActivity[i].getActivityId()==1) {
                setShapeColor(circleBtn, android.graphics.Color.rgb(218,112,214));
                setShapeColor(barBtn, android.graphics.Color.rgb(218,112,214));
            }
            else if(dailyActivity[i].getActivityId()==2) {
                setShapeColor(circleBtn, android.graphics.Color.rgb(0,191,255));
                setShapeColor(barBtn, android.graphics.Color.rgb(0,191,255));
            }
            else if(dailyActivity[i].getActivityId()==3) {
                setShapeColor(circleBtn, android.graphics.Color.rgb(50,205,50));
                setShapeColor(barBtn, android.graphics.Color.rgb(50,205,50));
            }
            else if(dailyActivity[i].getActivityId()==4) {
                setShapeColor(circleBtn, android.graphics.Color.rgb(240,230,140));
                setShapeColor(barBtn, android.graphics.Color.rgb(240,230,140));
            }
            LinearLayout activityCase = new LinearLayout(this);
            activityCase.setOrientation(LinearLayout.HORIZONTAL);
            activityCase.setLayoutParams(layoutParams);
            activityCase.addView(circleBtn);
            activityCase.addView(barBtn);
            activityList.addView(activityCase);
        }
    }
    /**
     * 设置 shape 的颜色
     * @param view
     * @param solidColor
     */
    public static void setShapeColor(View view,int solidColor){
        if(view == null){
            return;
        }
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        gradientDrawable.setColor(solidColor);
    }

    Button.OnClickListener pageChangeListener = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(PublicPage.this, SearchPage.class);
            startActivity(intent);
            PublicPage.this.finish();
        }
    };
    Button.OnClickListener dateChangeListener = new Button.OnClickListener() {
        public void onClick(View v){
            int n = v.getId();
            publicActivity.removeAllViews();
            initPublicActivity(activities[n]);
            dateColumn.removeAllViews();
            initDateColumn(n);
        }
    };
}



