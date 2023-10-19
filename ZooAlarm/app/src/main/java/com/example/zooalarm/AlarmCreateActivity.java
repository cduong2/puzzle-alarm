package com.example.zooalarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.UUID;

public class AlarmCreateActivity extends SingleFragmentActivity {
    private Button mBackButton;
    private static final String TAG = "AlarmCreateActivity";
    private static final String EXTRA_ALARM_ID =
            "com.example.zooalarm.alarm_id";
    public static Intent newIntent(Context packageContext, UUID alarmId) {
        Intent intent = new Intent(packageContext, AlarmCreateActivity.class);
        intent.putExtra(EXTRA_ALARM_ID, alarmId);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        Log.d(TAG, "onCreate(Bundle) called");
        UUID alarmId = (UUID) getIntent().getSerializableExtra(EXTRA_ALARM_ID);
        return AlarmFragment.newInstance(alarmId);
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
