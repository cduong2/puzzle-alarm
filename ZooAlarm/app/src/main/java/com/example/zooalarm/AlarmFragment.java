package com.example.zooalarm;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class AlarmFragment extends Fragment{
    private Alarm mAlarm;
    private EditText mAlarmField;
    private List<CheckBox> mDayCheckboxes;
    private CheckBox mIsFlashcards;
    private CheckBox mIsWordPuzzle;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlarm =new Alarm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);
        mAlarmField = (EditText) v.findViewById(R.id.alarm_time);
        mIsFlashcards = v.findViewById(R.id.is_flashcards);
        mIsWordPuzzle = v.findViewById(R.id.is_word_puzzle);
        mDayCheckboxes = new ArrayList<>();
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_mon));
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_tue));
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_wed));
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_thr));
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_fri));
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_sat));
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_sun));
        mAlarmField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mAlarm.setTime(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });
        // Set up listeners for day checkboxes
        for (CheckBox checkBox : mDayCheckboxes) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    updateDays();
                }
            });
        }
        mIsFlashcards.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAlarm.setActivity("Flashcards");
            }
        });

        mIsWordPuzzle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAlarm.setActivity("Puzzle");
            }
        });

        return v;
    }
    private void updateDays() {
        for (CheckBox checkBox : mDayCheckboxes) {
            if (checkBox.isChecked()) {
                // Add the day to the mDays property
                mAlarm.updateDays(checkBox.getText().toString());
            }
        }
    }
}
