package com.example.zooalarm.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmRepository {

    private AlarmDao mAlarmDao;
    private LiveData<List<Alarm>> mAllAlarms;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public AlarmRepository(Application application) {
//        AlarmRoomDatabase db = AlarmRoomDatabase.getInstance(application); //AlarmRoomDatabase.getDatabase(application);
        AlarmRoomDatabase db = AlarmRoomDatabase.getDatabase(application);
        this.mAlarmDao = db.alarmDao();
        this.mAllAlarms = this.mAlarmDao.getAllAlarms();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
//    public LiveData<List<Alarm>> getAllAlarms() {
//
//        Log.d("Database", "Alarms: " + mAllAlarms.toString());
//        return mAllAlarms;
//    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
//    public void insert(Alarm alarm) {
//        AlarmRoomDatabase.databaseWriteExecutor.execute(() -> {
//            mAlarmDao.insert(alarm);
//        });
//    }


    public void insert(Alarm alarm) {
        new InsertAlarmAsyncTask(mAlarmDao).execute(alarm);
    }
    public void update(Alarm alarm){
        new UpdateAlarmAsyncTask(mAlarmDao).execute(alarm);
    }

    public void delete(Alarm alarm) {
        new DeleteAlarmAsyncTask(mAlarmDao).execute(alarm);
    }

    public void deleteAllAlarms() {
        new DeleteAllAlarmAsyncTask(mAlarmDao).execute();
    }

    public LiveData<List<Alarm>> getAllAlarms() { // ?? this replaced christina's..
        return mAllAlarms;
    }


    // 1
    private static class UpdateAlarmAsyncTask extends AsyncTask<Alarm, Void, Void> {
        private AlarmDao mAlarmDao;

        private UpdateAlarmAsyncTask(AlarmDao alarmDao){
            this.mAlarmDao = mAlarmDao;
        }
        @Override
        protected Void doInBackground(Alarm... alarms) {
            mAlarmDao.updateAlarm(alarms[0]);
            return null;
        }
    }

    //2
    private static class DeleteAlarmAsyncTask extends AsyncTask<Alarm, Void, Void> {
        private AlarmDao mAlarmDao;

        private DeleteAlarmAsyncTask(AlarmDao alarmDao){
            this.mAlarmDao = mAlarmDao;
        }
        @Override
        protected Void doInBackground(Alarm... alarms) {
            mAlarmDao.deleteAlarm(alarms[0]);
            return null;
        }
    }

    //3
    private static class InsertAlarmAsyncTask extends AsyncTask<Alarm, Void, Void> {
        private AlarmDao mAlarmDao;

        private InsertAlarmAsyncTask(AlarmDao alarmDao){
            this.mAlarmDao = mAlarmDao;
        }
        @Override
        protected Void doInBackground(Alarm... alarms) {
            mAlarmDao.insert(alarms[0]);
            return null;
        }
    }


    //4
    private static class DeleteAllAlarmAsyncTask extends AsyncTask<Void, Void, Void> {
        private AlarmDao mAlarmDao;

        private DeleteAllAlarmAsyncTask(AlarmDao alarmDao){
            this.mAlarmDao = mAlarmDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mAlarmDao.deleteAll();
            return null;
        }
    }
}

