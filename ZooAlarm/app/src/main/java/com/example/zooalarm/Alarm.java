package com.example.zooalarm;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

public class Alarm {
    private UUID mId;
    private List<String> mDays;
    private String mTime;
    private String mActId;
    public Alarm(){
        mId = UUID.randomUUID();

    }
    public UUID getId(){
        return mId;
    }
    public List<String> getDays(){
        return mDays;
    }
    public void updateDays(String day){
        mDays.add(day);
    }
    public String getTime(){
        return mTime;
    }
    public void setTime(String time){
        mTime = time;
    }
    public String getActivity(){
        return mActId;
    }
    public void setActivity(String actId){
        mActId=actId;
    }
}
