package com.example.zooalarm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zooalarm.database.AlarmDbSchema.AlarmTable;

public class AlarmBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "alarmBase.db";
    public AlarmBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + AlarmTable.NAME+ "(" +
                " _id integer primary key autoincrement, " +
                AlarmTable.Cols.UUID + ", " +
                AlarmTable.Cols.TIME + ", " +
                AlarmTable.Cols.REPEAT + ", " +
                AlarmTable.Cols.ISON + ", " +
                AlarmTable.Cols.ACTIVITY +
                ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

}
