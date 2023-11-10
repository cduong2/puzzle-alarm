package com.example.zooalarm.ui.activities;

import androidx.fragment.app.Fragment;

import com.example.zooalarm.ui.fragments.AlarmListFragment;

public class AlarmListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new AlarmListFragment();
    }
}
