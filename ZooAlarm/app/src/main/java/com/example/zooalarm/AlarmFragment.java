package com.example.zooalarm;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlarmFragment extends Fragment {
    private Alarm mAlarm;
    private EditText mAlarmField;
    private CheckBox mRepeatCheckbox;
    private RadioGroup mActivityRadioGroup;
    private RadioButton mFlashCards;
    private RadioButton mPuzzle;

    private Button mBackButton;
    private Button mSubmitButton;
    private Button mDeleteButton;

    private static final String TAG = "AlarmFragment";
    private static final String ARG_ALARM_ID = "alarm_id";

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
        mAlarmField = v.findViewById(R.id.alarm_time);
        mRepeatCheckbox= v.findViewById(R.id.is_repeat);
        mActivityRadioGroup = v.findViewById(R.id.activity_radio_group);
        mFlashCards = v.findViewById(R.id.is_flashcards);
        mPuzzle = v.findViewById(R.id.is_word_puzzle);


        if (mAlarm!=null){
            mAlarmField.setText(mAlarm.getTime());
            mRepeatCheckbox.setChecked(mAlarm.getRepeat());
            if (mAlarm.getActivity().equals("flashcards")){
                mFlashCards.setChecked(true);
            }else{
                mPuzzle.setChecked(true);
            }
        }else{
            mAlarm=new Alarm();
        }
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

        // Set up listener for the alarm time EditText
        mAlarmField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAlarm.setTime(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
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
                alarmLab.addAlarm(mAlarm);
                startActivity(new Intent(getActivity(), AlarmListActivity.class));
            }
        });

        mDeleteButton = v.findViewById(R.id.delete_button);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmLab alarmLab = AlarmLab.get(getActivity());
                alarmLab.deleteAlarm(mAlarm.getId());
                startActivity(new Intent(getActivity(), AlarmListActivity.class));
            }
        });


        return v;
    }

}
