package com.maimai.zz.maimai;

import android.app.Activity;
import android.content.SharedPreferences;

import cn.bmob.v3.BmobObject;

/**
 * Created by wangh on 2017/2/7.
 */

public class StartDate extends BmobObject {
    private Integer year=2017;
    private Integer month=2;
    private Integer day=10;

    public StartDate(Activity activity){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("starDate", Activity.MODE_PRIVATE);
        if(sharedPreferences.getInt("year",0)!=0){
            year=sharedPreferences.getInt("year",0);
            month=sharedPreferences.getInt("month",0);
            day=sharedPreferences.getInt("day",0);
        }
    }

    public void updateStartDate(StartDate startDate){
        year = startDate.year;
        month = startDate.month;
        day = startDate.day;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }
}