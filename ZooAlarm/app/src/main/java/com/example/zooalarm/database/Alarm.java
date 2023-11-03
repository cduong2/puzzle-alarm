package com.example.zooalarm.database;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

public class Alarm {
    private UUID mId;
    private Boolean mRepeat=true;
    private Boolean mOn=true;
    private String mTime;
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
    public Boolean getRepeat(){
        return mRepeat;
    }
    public Boolean getOn(){
        return mOn;
    }


    public void setTime(String time){
        mTime = time;
    }
    public void setRepeat(Boolean repeat){
        mRepeat = repeat;
    }
    public void setOn(Boolean on){
        mOn = on;
    }


}