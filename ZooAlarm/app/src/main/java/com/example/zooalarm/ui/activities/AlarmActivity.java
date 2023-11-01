package com.example.zooalarm.ui.activities;

import androidx.fragment.app.Fragment;

import com.example.zooalarm.ui.fragments.AlarmFragment;

public class AlarmActivity extends SingleFragmentActivity {
    /** Called when the activity is first created. */
    @Override
    protected Fragment createFragment() {
        return new AlarmFragment();
    }
}