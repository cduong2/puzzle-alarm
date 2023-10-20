package com.example.zooalarm;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

@Entity(tableName = "Alarm")
public class Alarm {
    @ColumnInfo(name="mId")
    @PrimaryKey(autoGenerate = true)
    private UUID mId;
    @ColumnInfo(name = "mRepeat")
    private Boolean mRepeat;
    @ColumnInfo(name = "mOn")
    private Boolean mOn;
    @ColumnInfo(name = "mTime")
    private String mTime;
    @ColumnInfo(name = "mActId")
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
