package com.example.zooalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mWeatherButton;
    private Button mAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWeatherButton = (Button) findViewById(R.id.weather_button);
        mWeatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        R.string.weather_toast, Toast.LENGTH_SHORT).show();
            }
        });
        mAlarmButton = (Button) findViewById(R.id.alarm_button);
        mAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        R.string.alarm_toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}