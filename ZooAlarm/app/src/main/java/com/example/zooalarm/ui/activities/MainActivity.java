package com.example.zooalarm.ui.activities;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission.WAKE_LOCK;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

import com.example.zooalarm.R;
import com.example.zooalarm.database.AlarmReceiver;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mWeatherButton;
    private Button mAlarmButton;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        mWeatherButton = (Button) findViewById(R.id.weather_button);
        mWeatherButton.setOnClickListener(this);
        mAlarmButton = (Button) findViewById(R.id.alarm_button);
        mAlarmButton.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{POST_NOTIFICATIONS}, 1);
        }
        //NOT SURE ABT THESE
        if (ContextCompat.checkSelfPermission(this, INTERNET) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{INTERNET}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, VIBRATE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{VIBRATE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, WAKE_LOCK) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{WAKE_LOCK}, 1);
        }
        if(AlarmReceiver.r!=null) {
            AlarmReceiver.r.stop();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);

            if (alarmManager.canScheduleExactAlarms()){
                Log.v("CREATE", "alarm can be created");

            }else{
                Log.v("REQUEST", "alarm can NOT be created, sending user to settings");
                startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, Uri.parse("package:"+ getPackageName())));
            }
        }


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

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.weather_button){
            startActivity(new Intent(this, WeatherActivity.class));

        }else if(viewId==R.id.alarm_button){
            startActivity(new Intent(this, AlarmListActivity.class));

        }
    }
}