package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;


public class SearchPage extends AppCompatActivity {

    private static String[] testHistory = {"科研","创新","研究生","智能汽车大赛",
            "佐贺偶像是传奇","口腔喷剂","悲惨世界","简明物理化学","魂","1024节",
            "环境学院","神奇口袋","KDA","1001","すバらしい","大学物理","充电台灯",
            "毕业剧","philips","Python"};
    private LinearLayout historyView, resultView, switchView, cover;
    private SearchView browser;
    private Button cancel;
    private ListView mListView;
    private static String[] mStrs = {"AAA","BBB"} ;
    private int[] idCollector={R.id.firstDateSelect_btn,R.id.firstTimeSelect_btn,1000};
    Activity activity1 = new Activity(1,"8:00","10:00","智能车大赛真好玩啊哈哈哈哈哈哈哈啊哈哈","科创","六教6A214","汽车系科协");
    Activity activity2 = new Activity(2,"0:00","24:00","编程序真TM好玩啊哈水电费的说法呢发送到哈","计算机","没啥地点","拒绝熬夜组");
    Activity activity3 = new Activity(3,"16:00","20:00","生命学院学生节欢迎大家一起来看很好看的","社工","大礼堂","生命学院学生会");
    Activity activity4 = new Activity(4,"12:00","20:00","赶紧去学习吧哈哈哈哈哈哈哈哈哈哈哈哈","学习","#10 618","我");
    Activity[] resultCase = {activity1,activity2,activity3,activity4};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        browser = findViewById(R.id.browser);
        browser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                switchView.removeAllViews();
                View v = getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                resultView.removeAllViews();
                historyView.removeAllViews();
                cover.removeAllViews();
                initSwitchView();
                initResultView(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    mListView.clearTextFilter();
                }else{
                    mListView.clearTextFilter();
                }
                return false;
            }
        });
        mListView = findViewById(R.id.listView);
        mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrs));
        mListView.setTextFilterEnabled(true);
        cancel = findViewById(R.id.CancelButton);
        cancel.setOnClickListener(returnButtonListener);
        historyView = findViewById(R.id.historyView);
        resultView = findViewById(R.id.resultView);
        switchView = findViewById(R.id.switchColumn);
        cover = findViewById(R.id.cover);
        initHistoryView();
    }
    private void initHistoryView(){
        LinearLayout historyCase = historyView;
        int size = testHistory.length;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 1, 10, 1);
        LinearLayout.LayoutParams historyBarLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        historyBarLayoutParams.setMargins(0, 0, 0, 0);
        ArrayList<Button> childBtns = new ArrayList<>();
        int totalBtns = 0;

        LinearLayout historyHintBar = new LinearLayout(this);
        historyHintBar.setOrientation(LinearLayout.HORIZONTAL);
        historyHintBar.setLayoutParams(historyBarLayoutParams);
        historyHintBar.setPadding(25,5,0,5);
        TextView searchHistory = new TextView(this);
        searchHistory.setTextColor(Color.BLACK);
        searchHistory.setTextSize(16);
        searchHistory.setText("搜索历史");
        historyHintBar.addView(searchHistory);
        historyCase.addView(historyHintBar);

        for(int i = 0; i < size; i++){
            String item = testHistory[i];
            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int length= item.length();

            if(length < 4){
                itemParams.weight = 1;
                totalBtns++;
            }else if(length < 8){
                itemParams.weight = 2;
                totalBtns+=2;
            }else{
                itemParams.weight = 3;
                totalBtns+=3;
            }

            itemParams.width = 0;
            itemParams.setMargins(5, 5, 5, 5);
            Button childBtn = (Button) LayoutInflater.from(this).inflate(R.layout.history_button, null);
            childBtn.setText(item);
            childBtn.setOnClickListener(buttonOnClick);
            childBtn.setTag(item);
            childBtn.setId(100+i);//按钮编号100-119,监听，执行刷新页面及搜索操作
            childBtn.setLayoutParams(itemParams);
            childBtns.add(childBtn);

            if(totalBtns >= 5){
                LinearLayout  horizLL = new LinearLayout(this);
                horizLL.setOrientation(LinearLayout.HORIZONTAL);
                horizLL.setLayoutParams(layoutParams);

                for(Button addBtn:childBtns){
                    horizLL.addView(addBtn);
                }
                historyCase.addView(horizLL);
                childBtns.clear();
                totalBtns = 0;
            }
        }
        //调整最后一行的样式
        if(!childBtns.isEmpty()){
            LinearLayout horizLL = new LinearLayout(this);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);
            horizLL.setLayoutParams(layoutParams);

            for(Button addBtn:childBtns){
                horizLL.addView(addBtn);
            }
            historyCase.addView(horizLL);
            childBtns.clear();
            totalBtns = 0;
        }
    }

    private void initResultView(String input){
        LinearLayout resultCase = resultView;
        LinearLayout.LayoutParams resultColumnLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        resultColumnLayoutParams.setMargins(0, 5, 0, 5);
        LinearLayout.LayoutParams resultSelectButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        resultSelectButtonParams.weight = 1;
        LinearLayout resultSelectColumn = new LinearLayout(this);
        resultSelectColumn.setOrientation(LinearLayout.HORIZONTAL);
        resultSelectColumn.setLayoutParams(resultColumnLayoutParams);
        Button leftButton = (Button) LayoutInflater.from(this).inflate(R.layout.result_select_button, null);
        Button middleButton = (Button) LayoutInflater.from(this).inflate(R.layout.result_select_button, null);
        Button rightButton = (Button) LayoutInflater.from(this).inflate(R.layout.result_select_button, null);
        leftButton.setText("全部日期");
        leftButton.setLayoutParams(resultSelectButtonParams);
        leftButton.setOnClickListener(resultSelectButtonListener);
        leftButton.setId(R.id.dateSelect_btn);
        middleButton.setText("全部时长");
        middleButton.setLayoutParams(resultSelectButtonParams);
        middleButton.setOnClickListener(resultSelectButtonListener);
        middleButton.setId(R.id.timeSelect_btn);
        rightButton.setText("全部标签");
        rightButton.setLayoutParams(resultSelectButtonParams);
        rightButton.setOnClickListener(resultSelectButtonListener);
        rightButton.setId(R.id.classificaitonSelect_btn);
        resultSelectColumn.addView(leftButton);
        resultSelectColumn.addView(middleButton);
        resultSelectColumn.addView(rightButton);
        resultCase.addView(resultSelectColumn);
        //利用input的值进行搜索算法实现


    }

    private void initSwitchView(){
        LinearLayout switchCase = switchView;
        LinearLayout.LayoutParams switchButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        switchButtonParams.weight = 1;
        Button privateButton = (Button) LayoutInflater.from(this).inflate(R.layout.private_button, null);
        Button publicButton = (Button) LayoutInflater.from(this).inflate(R.layout.public_button, null);
        Button settingButton = (Button) LayoutInflater.from(this).inflate(R.layout.setting_button, null);
        privateButton.setOnClickListener(switchButtonListener);
        publicButton.setOnClickListener(switchButtonListener);
        settingButton.setOnClickListener(switchButtonListener);
        privateButton.setLayoutParams(switchButtonParams);
        publicButton.setLayoutParams(switchButtonParams);
        settingButton.setLayoutParams(switchButtonParams);
        switchCase.addView(privateButton);
        switchCase.addView(publicButton);
        switchCase.addView(settingButton);
    }

    private void generateCover(int num, int id){
        LinearLayout coverBox = cover;
        if(num == 1){
            LinearLayout.LayoutParams switchColumnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams switchButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            switchButtonParams.weight = 1;
            coverBox.setBackgroundColor(Color.BLACK);
            coverBox.getBackground().setAlpha(160);
            LinearLayout switchColumn = new LinearLayout(this);
            switchColumn.setLayoutParams(switchColumnParams);
            Button firstButton = (Button) LayoutInflater.from(this).inflate(R.layout.result_select_button, null);
            Button secondButton = (Button) LayoutInflater.from(this).inflate(R.layout.result_select_button, null);
            Button thirdButton = (Button) LayoutInflater.from(this).inflate(R.layout.result_select_button, null);
            Button forthButton = (Button) LayoutInflater.from(this).inflate(R.layout.result_select_button, null);
            firstButton.setText("全部日期");
            firstButton.setLayoutParams(switchButtonParams);
            firstButton.setOnClickListener(resultSelectButtonListener);
            firstButton.setId(R.id.firstDateSelect_btn);
            secondButton.setText("0-7天");
            secondButton.setLayoutParams(switchButtonParams);
            secondButton.setOnClickListener(resultSelectButtonListener);
            secondButton.setId(R.id.secondDateSelect_btn);
            thirdButton.setText("8-15天");
            thirdButton.setLayoutParams(switchButtonParams);
            thirdButton.setOnClickListener(resultSelectButtonListener);
            thirdButton.setId(R.id.thirdDateSelect_btn);
            forthButton.setText("16-30天");
            forthButton.setLayoutParams(switchButtonParams);
            forthButton.setOnClickListener(resultSelectButtonListener);
            forthButton.setId(R.id.forthDateSelect_btn);
            switch(id){
                case R.id.firstDateSelect_btn:
                    firstButton.setBackgroundColor(Color.rgb(237,189,101));
                    break;
                case R.id.secondDateSelect_btn:
                    secondButton.setBackgroundColor(Color.rgb(237,189,101));
                    break;
                case R.id.thirdDateSelect_btn:
                    thirdButton.setBackgroundColor(Color.rgb(237,189,101));
                    break;
                case R.id.forthDateSelect_btn:
                    forthButton.setBackgroundColor(Color.rgb(237,189,101));
                    break;
            }
            switchColumn.addView(firstButton);
            switchColumn.addView(secondButton);
            switchColumn.addView(thirdButton);
            switchColumn.addView(forthButton);
            cover.addView(switchColumn);
            Button coverButton = new Button(this);
            coverButton.setLayoutParams(switchButtonParams);
            coverButton.setBackgroundColor(Color.BLACK);
            coverButton.getBackground().setAlpha(0);
            coverButton.setId(R.id.blank_btn);
            coverButton.setOnClickListener(resultSelectButtonListener);
            cover.addView(coverButton);
        }
        else if(num == 2){
            LinearLayout.LayoutParams switchColumnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams switchButtonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            switchButtonParams.weight = 1;
            coverBox.setBackgroundColor(Color.BLACK);
            coverBox.getBackground().setAlpha(160);
            LinearLayout switchColumn = new LinearLayout(this);
            switchColumn.setLayoutParams(switchColumnParams);
            Button firstButton = (Button) LayoutInflater.from(this).inflate(R.layout.result_select_button, null);
            Button secondButton = (Button) LayoutInflater.from(this).inflate(R.layout.result_select_button, null);
            Button thirdButton = (Button) LayoutInflater.from(this).inflate(R.layout.result_select_button, null);
            Button forthButton = (Button) LayoutInflater.from(this).inflate(R.layout.result_select_button, null);
            firstButton.setText("全部时长");
            firstButton.setLayoutParams(switchButtonParams);
            firstButton.setOnClickListener(resultSelectButtonListener);
            firstButton.setId(R.id.firstTimeSelect_btn);
            secondButton.setText("0-1小时");
            secondButton.setLayoutParams(switchButtonParams);
            secondButton.setOnClickListener(resultSelectButtonListener);
            secondButton.setId(R.id.secondTimeSelect_btn);
            thirdButton.setText("1-3小时");
            thirdButton.setLayoutParams(switchButtonParams);
            thirdButton.setOnClickListener(resultSelectButtonListener);
            thirdButton.setId(R.id.thirdTimeSelect_btn);
            forthButton.setText("3小时以上");
            forthButton.setLayoutParams(switchButtonParams);
            forthButton.setOnClickListener(resultSelectButtonListener);
            forthButton.setId(R.id.forthTimeSelect_btn);
            switch(id){
                case R.id.firstTimeSelect_btn:
                    firstButton.setBackgroundColor(Color.rgb(237,189,101));
                    break;
                case R.id.secondTimeSelect_btn:
                    secondButton.setBackgroundColor(Color.rgb(237,189,101));
                    break;
                case R.id.thirdTimeSelect_btn:
                    thirdButton.setBackgroundColor(Color.rgb(237,189,101));
                    break;
                case R.id.forthTimeSelect_btn:
                    forthButton.setBackgroundColor(Color.rgb(237,189,101));
                    break;
            }
            switchColumn.addView(firstButton);
            switchColumn.addView(secondButton);
            switchColumn.addView(thirdButton);
            switchColumn.addView(forthButton);
            cover.addView(switchColumn);
            Button coverButton = new Button(this);
            coverButton.setLayoutParams(switchButtonParams);
            coverButton.setBackgroundColor(Color.BLACK);
            coverButton.getBackground().setAlpha(0);
            coverButton.setId(R.id.blank_btn);
            coverButton.setOnClickListener(resultSelectButtonListener);
            cover.addView(coverButton);
        }
        else if(num == 3){

        }
    }


    Button.OnClickListener returnButtonListener = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(SearchPage.this, PublicPage.class);
            startActivity(intent);
            SearchPage.this.finish();
        }
    };
    Button.OnClickListener buttonOnClick = new Button.OnClickListener() {
        public void onClick(View v) {
            int n = v.getId();
            //执行搜索算法
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            resultView.removeAllViews();
            historyView.removeAllViews();
            initSwitchView();
            initResultView(testHistory[n-100]);
        }
    };
    Button.OnClickListener switchButtonListener = new Button.OnClickListener() {
        public void onClick(View v) {
            if(v.getId()==R.id.private_btn) {
                Intent intent = new Intent(SearchPage.this, PublicPage.class);
                startActivity(intent);
                SearchPage.this.finish();
                //现在是跳转到public，之后合并再说
            }
            else if(v.getId()==R.id.public_btn){
                Intent intent = new Intent(SearchPage.this, PublicPage.class);
                startActivity(intent);
                SearchPage.this.finish();
            }
            else if(v.getId()==R.id.setting_btn){
                Intent intent = new Intent(SearchPage.this, PublicPage.class);
                startActivity(intent);
                SearchPage.this.finish();
                //现在是跳转到public，之后合并再说
            }
        }
    };
    Button.OnClickListener resultSelectButtonListener = new Button.OnClickListener() {
        public void onClick(View v) {
            if(v.getId()==R.id.dateSelect_btn) {
                generateCover(1,idCollector[0]);

            }
            else if(v.getId()==R.id.timeSelect_btn){
                generateCover(2,idCollector[1]);
            }
            else if(v.getId()==R.id.classificaitonSelect_btn){
                generateCover(3,idCollector[2]);
            }
            else if(v.getId()==R.id.firstDateSelect_btn){
                cover.removeAllViews();
                cover.getBackground().setAlpha(0);
                idCollector[0] = R.id.firstDateSelect_btn;
            }
            else if(v.getId()==R.id.secondDateSelect_btn){
                cover.removeAllViews();
                cover.getBackground().setAlpha(0);
                idCollector[0] = R.id.secondDateSelect_btn;
            }
            else if(v.getId()==R.id.thirdDateSelect_btn){
                cover.removeAllViews();
                cover.getBackground().setAlpha(0);
                idCollector[0] = R.id.thirdDateSelect_btn;
            }
            else if(v.getId()==R.id.forthDateSelect_btn){
                cover.removeAllViews();
                cover.getBackground().setAlpha(0);
                idCollector[0] = R.id.forthDateSelect_btn;
            }
            else if(v.getId()==R.id.firstTimeSelect_btn){
                cover.removeAllViews();
                cover.getBackground().setAlpha(0);
                idCollector[1] = R.id.firstTimeSelect_btn;
            }
            else if(v.getId()==R.id.secondTimeSelect_btn){
                cover.removeAllViews();
                cover.getBackground().setAlpha(0);
                idCollector[1] = R.id.secondTimeSelect_btn;
            }
            else if(v.getId()==R.id.thirdTimeSelect_btn){
                cover.removeAllViews();
                cover.getBackground().setAlpha(0);
                idCollector[1] = R.id.thirdTimeSelect_btn;
            }
            else if(v.getId()==R.id.forthTimeSelect_btn){
                cover.removeAllViews();
                cover.getBackground().setAlpha(0);
                idCollector[1] = R.id.forthTimeSelect_btn;
            }
            else {
                cover.removeAllViews();
                cover.getBackground().setAlpha(0);
            }
        }
    };
}
