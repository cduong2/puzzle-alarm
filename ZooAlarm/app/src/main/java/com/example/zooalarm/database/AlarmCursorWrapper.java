package com.example.zooalarm.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.zooalarm.database.Alarm;
import com.example.zooalarm.database.AlarmDbSchema;

import java.util.UUID;

public class AlarmCursorWrapper extends CursorWrapper {
    public AlarmCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Alarm getAlarm() {
        String uuidString = getString(getColumnIndex(AlarmDbSchema.AlarmTable.Cols.UUID));
        String time = getString(getColumnIndex(AlarmDbSchema.AlarmTable.Cols.TIME));
        String title = getString(getColumnIndex(AlarmDbSchema.AlarmTable.Cols.TITLE));
        int isOn = getInt(getColumnIndex(AlarmDbSchema.AlarmTable.Cols.ISON));
        Alarm alarm = new Alarm(UUID.fromString(uuidString));
        alarm.setTime(time);
        alarm.setTitle(title);
        alarm.setOn(isOn != 0);

        return alarm;
    }
}
