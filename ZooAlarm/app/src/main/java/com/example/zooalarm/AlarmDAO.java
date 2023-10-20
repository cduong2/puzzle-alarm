package com.example.zooalarm;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

// an interface to perform CRUD operations in database

@Dao
public interface AlarmDAO {

    @Insert
    public void addAlarm(Alarm alarm);

    @Update
    public void updateAlarm(Alarm alarm);

    @Delete
    public void deleteAlarm(Alarm alarm);

    @Query("select * from alarm") // * =? mTime, mDays, mActId
    public List<Alarm> getAllAlarm();

    @Query("select * from alarm where mId==:mId")
    public Alarm getAlarm(Integer mId);
}
