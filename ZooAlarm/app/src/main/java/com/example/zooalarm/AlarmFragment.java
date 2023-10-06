package com.example.zooalarm;

import android.content.Intent;
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

public class AlarmFragment extends Fragment {
    private Alarm mAlarm;
    private EditText mAlarmField;
    private List<CheckBox> mDayCheckboxes;
    private RadioGroup mActivityRadioGroup;
    private Button mBackButton;
    private static final String TAG = "AlarmFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlarm = new Alarm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);
        Log.d(TAG, "onCreateView: Fragment view is being created.");
        mAlarmField = v.findViewById(R.id.alarm_time);

        mDayCheckboxes = new ArrayList<>();
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_mon));
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_tue));
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_wed));
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_thr));
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_fri));
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_sat));
        mDayCheckboxes.add((CheckBox) v.findViewById(R.id.is_sun));


        // Set up listeners for day checkboxes
        for (CheckBox checkBox : mDayCheckboxes) {
            if (checkBox != null) { // Check for null
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (mAlarm != null) { // Check for null
                           // mAlarm.updateDays(checkBox.getText().toString());
                            Toast.makeText(getActivity(), checkBox.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        // Initialize radio group for activity selection
        mActivityRadioGroup = v.findViewById(R.id.activity_radio_group);
        // Set up listener for the radio group (activity selection)
        mActivityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.is_flashcards) {
                    mAlarm.setActivity("Flashcards");
                } else if (checkedId == R.id.is_word_puzzle) {
                    mAlarm.setActivity("Word Puzzle");
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
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        return v;
    }

}
