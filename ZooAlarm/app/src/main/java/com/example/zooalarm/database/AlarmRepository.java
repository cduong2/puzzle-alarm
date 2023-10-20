package com.example.zooalarm.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlarmRepository {

    private AlarmDao mAlarmDao;
    private LiveData<List<Alarm>> mAllAlarms;
    private ExecutorService mExecutorService;

    public AlarmRepository(Application application) {
        AlarmRoomDatabase db = AlarmRoomDatabase.getDatabase(application);
        mAlarmDao = db.alarmDao();
        mAllAlarms = mAlarmDao.getAllAlarms();
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Alarm>> getAllAlarms() {
        return mAllAlarms;
    }

    public void deleteAlarm(Alarm alarm) {
        mExecutorService.execute(() -> {
            mAlarmDao.deleteAlarm(alarm);
        });
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Alarm alarm) {
        mExecutorService.execute(() -> {
            mAlarmDao.insert(alarm);
        });
    }
}

