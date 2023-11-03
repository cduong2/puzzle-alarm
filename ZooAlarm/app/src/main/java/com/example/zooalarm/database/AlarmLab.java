package com.example.zooalarm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zooalarm.database.AlarmDbSchema.AlarmTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlarmLab {
    private static AlarmLab sAlarmLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static AlarmLab get(Context context){
        if(sAlarmLab==null){
            sAlarmLab = new AlarmLab(context);
        }
        return sAlarmLab;
    }
    private AlarmLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new AlarmBaseHelper(mContext).getWritableDatabase();

    }
    public void addAlarm(Alarm a){
        ContentValues values = getContentValues(a);
        mDatabase.insert(AlarmTable.NAME, null, values);
    }
    public List<Alarm> getAlarms(){
        List<Alarm> alarms = new ArrayList<>();
        AlarmCursorWrapper cursor = queryAlarms(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                alarms.add(cursor.getAlarm());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return alarms;
    }
    public Alarm getAlarm(UUID id){
        AlarmCursorWrapper cursor = queryAlarms(
                AlarmTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getAlarm();
        } finally {
            cursor.close();
        }
    }

    public void updateAlarm(Alarm alarm) {
        String uuidString = alarm.getId().toString();
        ContentValues values = getContentValues(alarm);
        mDatabase.update(AlarmTable.NAME, values,
                AlarmTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }
    public void deleteAlarm(UUID id) {
        String uuidString = id.toString();
        mDatabase.delete(
                AlarmTable.NAME,
                AlarmTable.Cols.UUID + " = ?",
                new String[]{uuidString}
        );
    }
    private AlarmCursorWrapper queryAlarms(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                AlarmTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new AlarmCursorWrapper(cursor);
    }
    private static ContentValues getContentValues(Alarm alarm) {
        ContentValues values = new ContentValues();
        values.put(AlarmTable.Cols.UUID, alarm.getId().toString());
        values.put(AlarmTable.Cols.TIME, alarm.getTime());
        values.put(AlarmTable.Cols.REPEAT, alarm.getRepeat()? 1 : 0);
        values.put(AlarmTable.Cols.ISON, alarm.getOn() ? 1 : 0);
        return values;
    }

}
