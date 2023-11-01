package com.example.zooalarm;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.credentials.CreateCredentialException;
import android.os.Bundle;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class AlarmFragment extends Fragment {
    private Alarm mAlarm;
    private CheckBox mRepeatCheckbox;
    private RadioGroup mActivityRadioGroup;
    private RadioButton mFlashCards;
    private RadioButton mPuzzle;
    private TextView mTimePicker;
    private Button mBackButton;
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
        if (alarmId!=null) {
            mAlarm = AlarmLab.get(getActivity()).getAlarm(alarmId);
            mUpdate=true;

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
        mActivityRadioGroup = v.findViewById(R.id.activity_radio_group);
        mFlashCards = v.findViewById(R.id.is_flashcards);
        mPuzzle = v.findViewById(R.id.is_word_puzzle);
        mTimePicker=v.findViewById(R.id.selectedTime);

        if (mAlarm!=null){
            mTimePicker.setText(mAlarm.getTime());
            mRepeatCheckbox.setChecked(mAlarm.getRepeat());
            if (mAlarm.getActivity().equals("flashcards")){
                mFlashCards.setChecked(true);
            }else{
                mPuzzle.setChecked(true);
            }
        }else{
            mAlarm=new Alarm();
        }
        mTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showTimePicker();
            }
        });
        mRepeatCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mAlarm.setRepeat(isChecked);
            } });



        // Set up listener for the radio group (activity selection)
        mActivityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.is_flashcards) {
                    mAlarm.setActivity("flashcards");
                } else if (checkedId == R.id.is_word_puzzle) {
                    mAlarm.setActivity("puzzle");
                }
            }
        });



        mBackButton = v.findViewById(R.id.home_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AlarmListActivity.class));
            }
        });
        mSubmitButton = v.findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmLab alarmLab = AlarmLab.get(getActivity());
                if (mUpdate){
                    alarmLab.updateAlarm(mAlarm);
                    startActivity(new Intent(getActivity(), AlarmListActivity.class));
                }else {
                    alarmLab.addAlarm(mAlarm);
                    startActivity(new Intent(getActivity(), AlarmListActivity.class));
                }
                setAlarm();
            }
        });

        mDeleteButton = v.findViewById(R.id.delete_button);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmLab alarmLab = AlarmLab.get(getActivity());
                alarmLab.deleteAlarm(mAlarm.getId());

                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(getContext(),0, intent, PendingIntent.FLAG_IMMUTABLE);
                if(alarmManager==null){
                    alarmManager=(AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);

                }
                alarmManager.cancel(pendingIntent);
                Toast.makeText(getContext(),"Alarm Deleted", Toast.LENGTH_SHORT);

                startActivity(new Intent(getActivity(), AlarmListActivity.class));

            }
        });


        return v;
    }

    private void setAlarm() {
        alarmManager=(AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(),0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(getContext(),"Alarm Set", Toast.LENGTH_SHORT);
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
                    time=String.format("%02d", (picker.getHour()-12)+":"+String.format("%02d",picker.getMinute())+" PM");
                    mTimePicker.setText(time);
                    mAlarm.setTime(time);

                }else{
                    time=picker.getHour()+":" + picker.getMinute()+" AM";
                    mTimePicker.setText(time);
                    mAlarm.setTime(time);

                }
                cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, picker.getHour());
                cal.set(Calendar.MINUTE, picker.getMinute());
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
            }
        });

    }

}
