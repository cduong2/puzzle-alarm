package com.example.zooalarm.database;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

@Entity(tableName = "alarm_table")
public class Alarm {
    @ColumnInfo(name="id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="mId")
    private UUID mUuid;
    @ColumnInfo(name = "mRepeat")
    private Boolean mRepeat;
    @ColumnInfo(name = "mOn")
    private Boolean mOn;
    @ColumnInfo(name = "mTime")
    private String mTime;
    @ColumnInfo(name = "mActId")
    private String mActId;
    public Alarm(){
        mUuid = UUID.randomUUID();

    }
    public void setId(Integer i){
        id = i;
    }
    public Integer getId(){
        return id;
    }
    public UUID getUuid(){
        return mUuid;
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
    public String getActId(){
        return mActId;
    }

    public void setUuid(UUID id){
        mUuid=id;
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

    public void setActId(String actId){
        mActId=actId;
    }
}
