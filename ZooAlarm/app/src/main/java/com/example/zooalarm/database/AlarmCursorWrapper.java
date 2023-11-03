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
        int isOn = getInt(getColumnIndex(AlarmDbSchema.AlarmTable.Cols.ISON));
        int isRepeat = getInt(getColumnIndex(AlarmDbSchema.AlarmTable.Cols.REPEAT));
        Alarm alarm = new Alarm(UUID.fromString(uuidString));
        alarm.setTime(time);
        alarm.setOn(isOn != 0);
        alarm.setRepeat(isRepeat != 0);

        return alarm;
    }
}
