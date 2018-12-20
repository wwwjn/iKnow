package com.example.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.graphics.Color;
import android.content.Intent;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FeedbackPage extends AppCompatActivity {
    private int TagNum = 3;
    private Button Back;
    private Button[] ButtonTag = new Button[TagNum];
    private boolean[] TagFlag = new boolean[TagNum];
    private EditText FeedBack;
    private Button Submit;
    private String[] TagContent = {"功能建议", "使用问题", "内容相关"};
    private TextView SubmitSucceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_page);

        Back = findViewById(R.id.ButtonBack);
        Back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(FeedbackPage.this, PrivatePage.class);  //之后更改--------
                startActivity(intent);
            }
        });

        for(int i = 0; i < TagNum; i++){
            ButtonTag[i] = findViewById(R.id.ButtonTag1 + i);
            ButtonTag[i].setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    int n = v.getId() - R.id.ButtonTag1;
                    if(TagFlag[n] == false){
                        ButtonTag[n].setBackgroundColor(Color.parseColor("#64c6a121"));
                        TagFlag[n] = true;
                    }
                    else{
                        ButtonTag[n].setBackgroundColor(Color.parseColor("#b9b6b6"));
                        TagFlag[n] = false;
                    }
                }
            });
        }

        FeedBack = findViewById(R.id.FeedBack);

        Submit = findViewById(R.id.ButtonSubmit);
        Submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Submit submit = new Submit();
                submit.start();
                try{
                    submit.join();
                }catch(Exception e){
                    e.printStackTrace();
                }
                SubmitSucceed.setVisibility(View.VISIBLE);
            }
        });

        SubmitSucceed = findViewById(R.id.SubmitSucceed);
    }

    private class Submit extends Thread{
        public void run() {
            try {
                String feedback = FeedBack.getText().toString();

                URL url = new URL("反馈地址");  //后面修改------------
                HttpURLConnection connection =  (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                Log.i("Connection", feedback);
                OutputStream os = connection.getOutputStream();
                os.write(feedback.getBytes());
                os.flush();
                os.close();

                Log.i("Connection", String.valueOf(connection.getResponseCode()));
                if (connection.getResponseCode() == 200) {
                    String result = StreamToString(connection.getInputStream());
                    Log.i("Connection", result);
                }
                else{
                    Log.i("Connection", "Fail");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String StreamToString(InputStream is) {
        //把输入流转换成字符串
        try {
            ByteArrayOutputStream Baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1)
                Baos.write(buffer, 0, len);
            String result = Baos.toString();
            is.close();
            Baos.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
