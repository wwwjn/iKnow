package com.example.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.graphics.Color;
import android.content.Intent;

public class FeedbackPage extends AppCompatActivity {
    private Button Buttonback;
    private Button buttonf, buttonu, buttonc;
    private boolean flagf = false, flagu = false, flagc = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_page);

        Buttonback = findViewById(R.id.ButtonBack);
        Buttonback.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(FeedbackPage.this, PrivatePage.class);
                startActivity(intent);
            }
        });

        buttonf = findViewById(R.id.buttonf);
        buttonu = findViewById(R.id.buttonu);
        buttonc = findViewById(R.id.buttonc);
        buttonf.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(flagf == false){
                    buttonf.setBackgroundColor(Color.parseColor("#64c6a121"));
                    flagf = true;
                }
                else{
                    buttonf.setBackgroundColor(Color.parseColor("#b9b6b6"));
                    flagf = false;
                }
            }
        });
        buttonu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(flagu == false){
                    buttonu.setBackgroundColor(Color.parseColor("#64c6a121"));
                    flagu = true;
                }
                else{
                    buttonu.setBackgroundColor(Color.parseColor("#b9b6b6"));
                    flagu = false;
                }
            }
        });
        buttonc.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(flagc == false){
                    buttonc.setBackgroundColor(Color.parseColor("#64c6a121"));
                    flagc = true;
                }
                else{
                    buttonc.setBackgroundColor(Color.parseColor("#b9b6b6"));
                    flagc = false;
                }
            }
        });
    }
}
