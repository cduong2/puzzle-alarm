package com.example.zooalarm;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

public class Alarm {
    private UUID mId;
    private Boolean mRepeat;
    private Boolean mOn;
    private String mTime;
    private String mActId;
    public Alarm(){
        mId = UUID.randomUUID();

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
