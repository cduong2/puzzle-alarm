package com.example.zooalarm;

import androidx.fragment.app.Fragment;

public class AlarmListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new AlarmListFragment();
    }
}
