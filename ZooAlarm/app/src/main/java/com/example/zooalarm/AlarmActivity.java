package com.example.zooalarm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class AlarmActivity extends SingleFragmentActivity {
    /** Called when the activity is first created. */
    @Override
    protected Fragment createFragment() {
        return new AlarmFragment();
    }
}