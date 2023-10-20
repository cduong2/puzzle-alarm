package com.example.zooalarm;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Alarm.class}, version = 1)
public abstract class AlarmDatabase extends RoomDatabase {

    public abstract AlarmDAO getAlarmDAO();

}
