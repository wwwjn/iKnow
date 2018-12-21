package com.example.test;

public class Activity {
    private int activityId;
    private int year;
    private int month;
    private int day;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private String introduction;
    private String mainLabel;
    private String subLabel;
    private String activityLabel;
    private String place;
    private String host;
    private String url;
    private String name;

    public Activity(int activityId, int year, int month, int day, int startHour, int startMinute, int endHour, int endMinute,
                    String introduction, String mainLabel, String subLabel, String activityLabel, String place, String host, String url, String name) {
        this.activityId = activityId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.introduction = introduction;
        this.mainLabel = mainLabel;
        this.subLabel = subLabel;
        this.activityLabel = activityLabel;
        this.place = place;
        this.host = host;
        this.url = url;
        this.name = name;
    }

    public int getActivityId() {return activityId;}

    public int getYear(){return year;}

    public int getMonth(){return month;}

    public int getDay(){return day;}

    public int getStartHour(){return startHour;}

    public int getStartMinute(){return startMinute;}

    public int getEndHour(){return endHour;}

    public int getEndMinute(){return endMinute;}

    public String getIntroduction() {
        return introduction;
    }

    public String getMainLabel(){return mainLabel;}

    public String getSubLabel(){return subLabel;}

    public String getActicityLabel(){return activityLabel;}

    public String getPlace() {
        return place;
    }

    public String getHost() {
        return host;
    }

    public String getUrl(){return url;}

    public String getName(){return name;}
}
