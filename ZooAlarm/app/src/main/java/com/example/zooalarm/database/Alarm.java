package com.example.zooalarm.database;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

public class Alarm {
    private UUID mId;
    private Boolean mRepeat=true;
    private Boolean mOn=true;
    private String mTime;
    private String mTitle;

    public Alarm(){
        this(UUID.randomUUID());
    }
    public Alarm(UUID id) {
        mId = id;
    }
    public UUID getId(){
        return mId;
    }
    public String getTime(){
        return mTime;
    }
    public String getTitle(){
        return mTitle;
    }

    public Boolean getOn(){
        return mOn;
    }


    public void setTime(String time){
        mTime = time;
    }
    public void setTitle(String title){
        mTitle = title;
    }

    public void setOn(Boolean on){
        mOn = on;
    }


}
