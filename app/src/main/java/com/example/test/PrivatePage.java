package com.example.test;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import android.util.*;
import android.os.*;

import org.json.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.io.*;


public class PrivatePage extends AppCompatActivity {
    private int DateButtonNum = 20;
    private Button[] DateButton = new Button[DateButtonNum];
    private int CurrentDateId = 0;
    private String Username = "q";
    private String MyActivity;

    private void AddToMyActivity(Activity act){
        try{
            File file = new File(Environment.getExternalStorageDirectory(),"MyActivity.txt");
            JSONArray array;
            if (!file.exists()){
                file.createNewFile();
                SendToServer();
                Log.i("Connection", MyActivity);
                array = new JSONArray(MyActivity);
            }
            else{
                String data = GetData("MyActivity.txt");
                array = new JSONArray(data);
            }

            JSONObject Json = new JSONObject();
            Json.put("ActivityId",act.getActivityId());
            Json.put("Year",act.getYear());
            Json.put("Month",act.getMonth());
            Json.put("Day", act.getDay());
            Json.put("StartTime", act.getStartTime());
            Json.put("EndTime", act.getEndTime());
            Json.put("Introduction", act.getIntroduction());
            Json.put("Classification", act.getClassification());
            Json.put("Place", act.getPlace());
            Json.put("Host", act.getHost());
            array.put(Json);

            String content = String.valueOf(array);
            FileOutputStream os = new FileOutputStream(file);
            os.write(content.getBytes());
            os.close();
        }catch(Exception e){
            e.printStackTrace();
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

    private void SendToServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://iknow.gycis.me:8443/downloadData/getPrivateActivity");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    JSONObject Json = new JSONObject();  //把数据存成Json格式
                    Json.put("Username", Username);
                    String content = String.valueOf(Json);

                    OutputStream os = connection.getOutputStream();  //打开输出流传输数据
                    os.write(content.getBytes());
                    os.flush();
                    os.close();

                    Log.i("Connection", String.valueOf(connection.getResponseCode()));
                    if (connection.getResponseCode() == 200){
                        //Log.i("Connection", StreamToString(connection.getInputStream()));
                        JSONObject json = new JSONObject(StreamToString(connection.getInputStream()));
                        for(int i = 1; i < 6; i++){
                            Log.i("Connection", json.getString("Activity" + String.valueOf(i)));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void InitData(){
        Activity act1 = new Activity(1,2018, 12, 20,"08:00","10:00",
                "这是1220的活动","科创","文图","这是链接");
        Activity act2 = new Activity(2,2018, 12, 19,"08:00","10:00",
                "这是1219的活动","科创","文图","这是链接");
        Activity act3 = new Activity(3,2018, 12, 21,"08:00","10:00",
                "这是1221的活动","科创","文图","这是链接");

        AddToMyActivity(act1);
        AddToMyActivity(act2);
        AddToMyActivity(act3);
    }

    private String GetData(String FileName){
        try {
            File file = new File(Environment.getExternalStorageDirectory(),FileName);
            FileInputStream fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int len = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = fis.read(b)) != -1) {
                baos.write(b, 0, len);
            }
            String data = baos.toString();
            baos.close();
            fis.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void Init(){
        LinearLayout layout = findViewById(R.id.DateViewLayout);
        Calendar Current = Calendar.getInstance();
        Current.setTimeInMillis(System.currentTimeMillis());
        int CurrentDate = Current.get(Calendar.DATE);
        int CurrentWeekday = Current.get(Calendar.DAY_OF_WEEK);
        String[] Week = { "日\n", "一\n", "二\n", "三\n", "四\n", "五\n", "六\n"};
        for(int i = 0; i < DateButtonNum; i++){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getPixelsFromDp(50), LinearLayout.LayoutParams.MATCH_PARENT);
            DateButton[i] = new Button(this);
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
                }
            });
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
        //InitData();
        SendToServer();
        /*String data = GetData("MyActivity.txt");
        Log.i("Connection",data);
        try{
            JSONArray DataArray =  new JSONArray(data);
            for (int i = 0; i < DataArray.length(); i++) {
                JSONObject act = DataArray.getJSONObject(i);
                Log.i("Connection",String.valueOf(i));
                Log.i("Connection",act.getString("Introduction"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }*/
    }

    //点击"公共日历"按钮，进入“公共日历”页面
    public void gotoPublicPage(View view){
        Intent intent = new Intent(PrivatePage.this, PublicPage.class);
        startActivity(intent);
        PrivatePage.this.finish();
    }

    //点击"软件设置"按钮，进入“软件设置”页面
    public void gotoSettingPage(View view) {
        Intent intent = new Intent(PrivatePage.this, SettingPage.class);
        startActivity(intent);
        PrivatePage.this.finish();
    }
}
