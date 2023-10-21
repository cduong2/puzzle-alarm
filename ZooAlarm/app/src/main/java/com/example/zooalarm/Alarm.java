package com.example.zooalarm;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

public class Alarm {
    private UUID mId;
    private Boolean mRepeat=true;
    private Boolean mOn=true;
    private String mTime;
    private String mActId="puzzle";
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

    public String getActivity(){
        return mActId;
    }
    public void setActivity(String actId){
        mActId=actId;
    }
}
