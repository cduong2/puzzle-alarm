package com.example.zooalarm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.zooalarm.database.Alarm;
import com.example.zooalarm.database.AlarmRepository;

import java.util.List;

public class AlarmViewModel extends AndroidViewModel {

    private AlarmRepository mRepository;

    private final LiveData<List<Alarm>> mAllAlarms;

    public AlarmViewModel(@NonNull Application application){
        super(application);
        mRepository = new AlarmRepository(application);
        mAllAlarms = mRepository.getAllAlarms();
    }

    public void insert(Alarm alarm) {
        mRepository.insert(alarm);
    }

    public void update(Alarm alarm) {
        mRepository.update(alarm);
    }

    public void delete(Alarm alarm) {
        mRepository.delete(alarm);
    }

    public void deleteAllAlarms() {
        mRepository.deleteAllAlarms();
    }

//
//    public AlarmViewModel (Application application) {
//        super(application);
//        mRepository = new AlarmRepository(application);
//        mAllAlarms = mRepository.getAllAlarms();
//    }
//
    LiveData<List<Alarm>> getAllWords() { return mAllAlarms; }
//
//    public void insert(Alarm alarm) { mRepository.insert(alarm); }
}

