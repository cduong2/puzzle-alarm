package com.example.zooalarm;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlarmLab {
    private static AlarmLab sAlarmLab;
    private List<Alarm> mAlarms;
    public static AlarmLab get(Context context){
        if(sAlarmLab==null){
            sAlarmLab = new AlarmLab(context);
        }
        return sAlarmLab;
    }
    private AlarmLab(Context context){
        mAlarms = new ArrayList<>();
    }
    public void addAlarm(Alarm a){
        mAlarms.add(a);
    }
    public List<Alarm> getAlarms(){
        return mAlarms;
    }
    public Alarm getAlarm(UUID id){
        for (Alarm alarm: mAlarms){
            if (alarm.getId().equals(id)){
                return alarm;
            }
        }
        return null;
    }

}
