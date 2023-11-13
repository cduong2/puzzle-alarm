package com.example.zooalarm.ui.fragments;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
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

import java.util.Calendar;
import java.util.UUID;

public class AlarmFragment extends Fragment {
    private Alarm mAlarm;
    private CheckBox mRepeatCheckbox;
    private EditText mAlarmTitle;
    private Button mTimePicker;
    private Button mSubmitButton;
    private Button mDeleteButton;
    private Boolean mUpdate=false;
    private MaterialTimePicker picker;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()){
                Log.v("CREATE", "alarm can be created");

            }else{
                Log.v("REQUEST", "alarm can NOT be created, sending user to settings");

                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM, Uri.parse("package:"+ getContext().getPackageName())));
            }
        }
        if (alarmId!=null) {
            mAlarm = AlarmLab.get(getActivity()).getAlarm(alarmId);
            mUpdate=true;

        }

        createNotificationChannel();

    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = "zooalarmReminderChannel";
            String description ="Channel for Zoo Alarm";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("zooalarm", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        AlarmLab.get(getActivity()).updateAlarm(mAlarm);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);
        Log.d(TAG, "onCreateView: Fragment view is being created.");
        mRepeatCheckbox= v.findViewById(R.id.is_repeat);
        mTimePicker=v.findViewById(R.id.selectedTime);
        mAlarmTitle = v.findViewById(R.id.alarm_title);

        if (mAlarm!=null){
            mAlarmTitle.setText(mAlarm.getTitle());
            mTimePicker.setText(mAlarm.getTime());
            mRepeatCheckbox.setChecked(mAlarm.getRepeat());

        }else{
            mAlarm=new Alarm();
        }
        mTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showTimePicker();
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
        mRepeatCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mAlarm.setRepeat(isChecked);
            } });


        mSubmitButton = v.findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmLab alarmLab = AlarmLab.get(getActivity());
                if (mUpdate){
                    alarmLab.updateAlarm(mAlarm);
                    setAlarm();
                    startActivity(new Intent(getActivity(), AlarmListActivity.class));
                }else {
                    alarmLab.addAlarm(mAlarm);
                    setAlarm();
                    startActivity(new Intent(getActivity(), AlarmListActivity.class));
                }
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
                Log.v("DELETE", "alarm deleted");


                startActivity(new Intent(getActivity(), AlarmListActivity.class));

            }
        });


        return v;
    }

    private void setAlarm() {
        alarmManager =(AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(getActivity(),0,intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(getContext(),"Alarm Set", Toast.LENGTH_SHORT).show();
        String message = "alarm will go off in " + cal.getTimeInMillis() +" milliseconds";
        Log.v("CREATED", message);

    }

    private void showTimePicker() {
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();
        picker.show(getParentFragmentManager(), "zooalarm");
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String time;

                if(picker.getHour()>12){
                    time=(picker.getHour()-12)+":"+String.format("%02d",picker.getMinute())+" PM";
                }else{
                    time=picker.getHour()+":" + String.format("%02d",picker.getMinute())+" AM";
                }
                mTimePicker.setText(time);
                mAlarm.setTime(time);
                cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, picker.getHour());
                cal.set(Calendar.MINUTE, picker.getMinute());
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
            }
        });


    }

}
