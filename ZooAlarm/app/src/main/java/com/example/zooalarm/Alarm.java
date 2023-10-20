package com.example.zooalarm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.List;
import java.util.UUID;
//import androidx.room;
//import android.arch.persistence.Entity;

@Entity(tableName = "Alarm")
public class Alarm {
    @ColumnInfo(name="mId")
    @PrimaryKey(autoGenerate = true)
//    private UUID mId;
    private Integer mId;

    @ColumnInfo(name = "mDays")
    private Boolean mDays;

    @ColumnInfo(name = "mTime")
    private String mTime;

    @ColumnInfo(name = "mActId")
    private String mActId;


    @Ignore
    public Alarm(){

    }

    public Alarm(Boolean mDays, String mTime, String mActId){
        this.mDays = mDays;
        this.mTime = mTime;
        this.mActId = mActId;
    }
//    public Alarm(){
//        mId = UUID.randomUUID();
//
//    }


    public void setId(Integer id){ // ??
        mId = id;
    }
    public Integer getId(){
        return mId;
    }
    public Boolean getDays(){
        return mDays;
    }
    public void setDays(Boolean days){
        mDays = days;
    }
//    public void updateDays(String day){
//        mDays.add(day);
//    }
    public String getTime(){
        return mTime;
    }
    public void setTime(String time){
        mTime = time;
    }

    public String getActId(){ // ???
        return mActId;
    }
    public String getActivity(){
        return mActId;
    }
    public void setActivity(String actId){
        mActId=actId;
    }
}
