package com.example.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

public class DetailPage extends AppCompatActivity {

    TextView[] mTextview=new TextView[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        mTextview[0]=findViewById(R.id.activityname);
        mTextview[1]=findViewById(R.id.tv1);
        mTextview[2]=findViewById(R.id.tv2);
        mTextview[3]=findViewById(R.id.tv3);
        mTextview[4]=findViewById(R.id.tv4);
        mTextview[5]=findViewById(R.id.tv5);
        mTextview[6]=findViewById(R.id.tv6);
        setActivity();
    }

    private void setActivity(){
        mTextview[0].setText("活动名称");
        mTextview[1].setText("活动名称");
        mTextview[2].setText("8月1日");
        mTextview[3].setText("8：00");
        mTextview[4].setText("10：00");
        mTextview[5].setText("六教");
        mTextview[6].setText("工物系");
    }
}
