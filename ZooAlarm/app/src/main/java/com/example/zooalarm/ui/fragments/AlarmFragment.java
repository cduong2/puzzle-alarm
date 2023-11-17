package com.example.zooalarm.ui.fragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zooalarm.database.Alarm;
import com.example.zooalarm.database.AlarmLab;
import com.example.zooalarm.database.AlarmReceiver;
import com.example.zooalarm.R;
import com.example.zooalarm.ui.activities.AlarmListActivity;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class AlarmFragment extends Fragment {
    private Alarm mAlarm;
    private Boolean mUpdate=false;
    private Calendar cal;

    private AlarmManager alarmManager;
    private static final String TAG = "AlarmFragment";
    private static final String ARG_ALARM_ID = "alarm_id";
    private PendingIntent pendingIntent;

    public static AlarmFragment newInstance(UUID alarmId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ALARM_ID, alarmId);
        AlarmFragment fragment = new AlarmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID alarmId = (UUID) getArguments().getSerializable(ARG_ALARM_ID);
        alarmManager=(AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        createNotificationChannel();


        if (alarmId!=null) {
            mAlarm = AlarmLab.get(getActivity()).getAlarm(alarmId);
            mUpdate=true;

        }


    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = "zooalarmReminderChannel";
            String description ="Channel for Zoo Alarm";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("zooalarm", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (alarmManager != null) {
            AlarmLab.get(getActivity()).updateAlarm(mAlarm);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EditText mAlarmTitle;
        Button mTimePicker,mSubmitButton,mDeleteButton;

        View v = inflater.inflate(R.layout.fragment_alarm, container, false);
        Log.d(TAG, "onCreateView: Fragment view is being created.");
        mTimePicker=v.findViewById(R.id.selectedTime);
        mAlarmTitle = v.findViewById(R.id.alarm_title);

        if (mAlarm!=null){
            mAlarmTitle.setText(mAlarm.getTitle());
            String dateFormatted=AlarmLab.getTimeString(mAlarm.getTime());
            mTimePicker.setText(dateFormatted);

        }else{
            mAlarm=new Alarm();
        }
        mTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showTimePicker(mTimePicker);
            }
        });
        // Set up listener for the alarm time EditText
        mAlarmTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAlarm.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mSubmitButton = v.findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmLab alarmLab = AlarmLab.get(getActivity());
                if (mUpdate){
                    alarmLab.updateAlarm(mAlarm);
                }else {
                    alarmLab.addAlarm(mAlarm);
                }
                setAlarm();
                startActivity(new Intent(getActivity(), AlarmListActivity.class));
            }
        });

        mDeleteButton = v.findViewById(R.id.delete_button);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmLab alarmLab = AlarmLab.get(getActivity());
                alarmLab.deleteAlarm(mAlarm.getId());

                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(getActivity(),0, intent, PendingIntent.FLAG_IMMUTABLE);
                if(alarmManager==null){
                    alarmManager=(AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
                }
                alarmManager.cancel(pendingIntent);
                startActivity(new Intent(getActivity(), AlarmListActivity.class));

            }
        });


        return v;
    }

    @SuppressLint("ScheduleExactAlarm")
    private void setAlarm() {
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        }
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(getActivity(),0,intent, PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, mAlarm.getTime(), pendingIntent);
            Toast.makeText(getContext(), "Alarm Set", Toast.LENGTH_SHORT).show();
        }
    }
    private MaterialTimePicker picker;

    private void showTimePicker(Button mTimePicker) {
        if (picker==null) {
            picker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Select Alarm Time")
                    .build();
        }
        picker.show(getParentFragmentManager(), "zooalarm");
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, picker.getHour());
                cal.set(Calendar.MINUTE, picker.getMinute());
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                //add a day if the selected time already passed
                Calendar currentTime = Calendar.getInstance();
                if (cal.before(currentTime)) {
                    // If so, set the calendar day to the next day
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                }

                String dateFormatted=AlarmLab.getTimeString(cal.getTimeInMillis());
                mTimePicker.setText(dateFormatted);
                mAlarm.setTime(cal.getTimeInMillis());
            }
        });


    }

}
