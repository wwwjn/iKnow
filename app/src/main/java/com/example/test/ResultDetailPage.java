package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ResultDetailPage extends AppCompatActivity {

    TextView[] mTextview=new TextView[9];
    Button backButton,lastButton,tag1,tag2;
    int ActivityID;
    boolean getActivityflag=false;
    Activity temp = new Activity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityID = 3;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        mTextview[0]=findViewById(R.id.activityname);
        mTextview[1]=findViewById(R.id.tv1);
        mTextview[2]=findViewById(R.id.tv2);
        mTextview[3]=findViewById(R.id.tv3);
        mTextview[4]=findViewById(R.id.tv4);
        mTextview[5]=findViewById(R.id.tv5);
        mTextview[6]=findViewById(R.id.tv6);
        mTextview[7]=findViewById(R.id.tv7);
        mTextview[8]=findViewById(R.id.tv8);
        backButton=findViewById(R.id.back_button);
        lastButton=findViewById(R.id.last_button);
        tag1=findViewById(R.id.tag1);
        tag2=findViewById(R.id.tag2);
        Intent intent = getIntent();
        int resId = intent.getIntExtra("ActivityNum",0);
        System.out.println(resId);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(ResultDetailPage.this,PublicPage.class);
                startActivity(intent);
            }
        });
        GetActivityFromId(resId);
        setActivity();
    }
    protected void GetActivityFromId(final int i){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONObject Json = new JSONObject();  //把数据存成Json格式
                    Json.put("ActivityID", i);
                    String content = String.valueOf(Json);  //Json格式转成字符串来传输

                    URL url = new URL("https://iknow.gycis.me/downloadData/getDetail");  //不同的请求发送到不同的URL地址，见群里的“后端网页名字设计.docx”
                    HttpURLConnection connection =  (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    Log.i("Connection", content);
                    OutputStream os = connection.getOutputStream();  //打开输出流传输数据
                    os.write(content.getBytes());
                    os.flush();
                    os.close();
                    Log.i("Connection", String.valueOf(connection.getResponseCode()));  //如果ResponseCode=200说明和服务器
                    if (connection.getResponseCode() == 200) {
                        //以字符串格式读取服务器的返回内容，Register功能只需返回普通字符串，如果请求的是活动信息则将会返回Json格式的字符串，
                        //可以用形如JSONObject Json = new JSONObject(String)的语句把字符串转成Json格式
                        String result = StreamToString(connection.getInputStream());
                        JSONObject thisJson = new JSONObject(result);
                        Log.i("Connection", result);
                        temp.setActivityId(thisJson.getInt("Id"));
                        temp.setStartHour(thisJson.getInt("Start_hour"));
                        temp.setStartMinute(thisJson.getInt("Start_minute"));
                        temp.setEndHour(thisJson.getInt("End_hour"));
                        temp.setEndMinute(thisJson.getInt("End_minute"));
                        temp.setDay(thisJson.getInt("Day"));
                        temp.setMonth(thisJson.getInt("Month"));
                        temp.setYear(thisJson.getInt("Year"));
                        temp.setName(thisJson.getString("Name"));
                        temp.setPlace(thisJson.getString("Address"));
                        temp.setHost(thisJson.getString("Holder"));
                        temp.setMainLabel(thisJson.getString("Tag1"));
                        temp.setSubLabel(thisJson.getString("Tag2"));
                        temp.setActivityLabel(thisJson.getString("Tag3"));
                        temp.setIntroduction(thisJson.getString("Introduction"));
                        temp.setUrl(thisJson.getString("Url"));
                    }

                    else{
                        Log.i("Connection", "Fail");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
    private void setActivity(){
        try{
            Thread.sleep(500);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        mTextview[0].setText(temp.getName());
        mTextview[1].setText(temp.getName());
        mTextview[2].setText(temp.getYear()+"年"+temp.getMonth()+"月"+temp.getDay()+"日");
        if(temp.getStartMinute()<10) {
            mTextview[3].setText(String.valueOf(temp.getStartHour()) + ":0" +String.valueOf(temp.getStartMinute()));
        }
        else{
            mTextview[3].setText(String.valueOf(temp.getStartHour()) + ":" +String.valueOf(temp.getStartMinute()));
        }
        if(temp.getEndMinute()<10) {
            mTextview[4].setText(String.valueOf(temp.getEndHour()) + ":0" +String.valueOf(temp.getEndMinute()));
        }
        else{
            mTextview[4].setText(String.valueOf(temp.getEndHour()) + ":" +String.valueOf(temp.getEndMinute()));
        }
        mTextview[5].setText(temp.getPlace());
        mTextview[6].setText(temp.getHost());
        tag1.setText(temp.getMainLabel());
        tag2.setText(temp.getSubLabel());
        mTextview[7].setText("URL");
        mTextview[8].setText(temp.getIntroduction());
    }
}
