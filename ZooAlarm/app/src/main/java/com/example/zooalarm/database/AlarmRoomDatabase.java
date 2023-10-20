package com.example.zooalarm.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Alarm.class}, version = 1, exportSchema = false)
public abstract class AlarmRoomDatabase extends RoomDatabase {

    public abstract AlarmDao alarmDao();

    private static volatile AlarmRoomDatabase INSTANCE;

    //??
    public static synchronized AlarmRoomDatabase getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AlarmRoomDatabase.class, "alarm_room_database").fallbackToDestructiveMigration().addCallback(sRoomDatabaseCallback).build();
        }
        return INSTANCE;
    }
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AlarmRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AlarmRoomDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AlarmRoomDatabase.class, "alarm_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private AlarmDao alarmDao;

        private PopulateDbAsyncTask(AlarmRoomDatabase db){
            alarmDao = db.alarmDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Alarm alarm = new Alarm();
            alarm.setActId("Flashcards");
            alarm.setOn(true);
            alarm.setTime("9:00 AM");
            alarm.setRepeat(false);
            alarmDao.insert(alarm);
            alarm = new Alarm();
            alarm.setActId("Puzzle");
            alarm.setOn(true);
            alarm.setTime("8:10 AM");
            alarm.setRepeat(true);
            alarmDao.insert(alarm);
            return null;
        }
    }
//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//
//            // If you want to keep data through app restarts,
//            // comment out the following block
//            databaseWriteExecutor.execute(() -> {
//                // Populate the database in the background.
//                // If you want to start with more words, just add them.
//                AlarmDao dao = INSTANCE.alarmDao();
//                dao.deleteAll();
//
//                Alarm alarm = new Alarm();
//                alarm.setActId("Flashcards");
//                alarm.setOn(true);
//                alarm.setTime("9:00 AM");
//                alarm.setRepeat(false);
//                dao.insert(alarm);
////                Alarm alarm1 = new Alarm();
////                alarm1.setActId("Puzzle");
////                alarm1.setOn(true);
////                alarm1.setTime("8:10 AM");
////                alarm1.setRepeat(true);
////                dao.insert(alarm1);
//            });
//        }
//    };

}
