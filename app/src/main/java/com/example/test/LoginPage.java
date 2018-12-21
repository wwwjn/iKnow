package com.example.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import org.json.JSONObject;
import android.util.Log;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginPage extends AppCompatActivity {
    Button btn_login,btn_register;
    EditText Username,Password;
    TextView LoginUE, LoginPE;
    User Me=new User();
    boolean UsernameSucceedFlag = true;
    boolean PasswordSucceedFlag = true;

    class User{
        //存储用户的用户名、密码
        public String Username, Password;
        public void SetInformation(String Username, String Password){
            this.Username = Username;
            this.Password = Password;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Init();
    }

    protected void Init(){
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        LoginUE = findViewById(R.id.LoginUsernameError);
        LoginPE = findViewById(R.id.LoginPasswordError);
        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String username = Username.getText().toString();
                final String password = Password.getText().toString();
                LoginUE.setVisibility(View.INVISIBLE);
                LoginPE.setVisibility(View.INVISIBLE);
                UsernameSucceedFlag = true;
                PasswordSucceedFlag = true;
                Me.SetInformation(username, password);
                Login login = new Login();
                login.start();
                try{
                    login.join();
                }catch(Exception e){
                    e.printStackTrace();
                }
                if (UsernameSucceedFlag == false) {
                    LoginUE.setVisibility(View.VISIBLE);
                }else if(PasswordSucceedFlag==false){
                    LoginPE.setVisibility(View.VISIBLE);
                }else{
                    Intent intent = new Intent(LoginPage.this, PrivatePage.class);
                    startActivity(intent);
                    LoginPage.this.finish();
                }
            }
        });
        btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(intent);
                LoginPage.this.finish();
            }
        });
    }

    protected class Login extends Thread{
        public void run() {
            try{
                JSONObject Json = new JSONObject();  //把数据存成Json格式
                Json.put("Username", Me.Username);
                Json.put("Password", Me.Password);
                String content = String.valueOf(Json);  //Json格式转成字符串来传输

                URL url = new URL("https://iknow.gycis.me:8443");  //不同的请求发送到不同的URL地址，见群里的“后端网页名字设计.docx”
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
                    Log.i("Connection", result);
                    if(result.equals("username is not existed"))
                        UsernameSucceedFlag = false;
                    else if(result.equals("password is wrong"))
                        PasswordSucceedFlag = false;
                }
                else{
                    Log.i("Connection", "Fail");
                }
            }catch (Exception e) {
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

