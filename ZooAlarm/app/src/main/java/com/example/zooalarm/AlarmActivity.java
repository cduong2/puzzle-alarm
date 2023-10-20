package com.example.zooalarm;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class AlarmActivity extends SingleFragmentActivity {
    /** Called when the activity is first created. */


//    ArrayList<Alarm> alarmList;

//    @Override
//    public View onCreateView

    @Override
    protected Fragment createFragment() {
        return new AlarmFragment(this);
    } // to access Context in fragment. context is "this"
}