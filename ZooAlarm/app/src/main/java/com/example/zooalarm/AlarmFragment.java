package com.example.zooalarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import androidx.room.ColumnInfo;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AlarmFragment extends Fragment {
    private Alarm mAlarm;
    private EditText mAlarmField;
    private List<CheckBox> mDayCheckboxes;
    private RadioGroup mActivityRadioGroup;
    private Button mBackButton;
    private Button mSubmitButton; //ciDBi
    private static final String TAG = "AlarmFragment";

    // context issue with getApplicationContext() https://stackoverflow.com/questions/16678763/the-method-getapplicationcontext-is-undefined
    Context c;
    public AlarmFragment(Context context){
        c = context;
    }

    // creating and initialize the actual database instance (might go somewhere else?)
    AlarmDatabase alarmDB; //ciDBi

    // 17:41 in tutorial moved this part to AlarmActivity // lot of work to create a separate view rn since i'm cramming this thing in
    List<Alarm> alarmList;`


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
//                           mAlarm.updateDays(checkBox.getText().toString());
                            mAlarm.setDays(true); // !! idk if this is always going to be true
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


        //ciDBi
        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                super.onDestructiveMigration(db);
            }
        };

        // ?? idk why getApplicationContext() doesn't work // because context is needed.. i think this is fixed?
        // no, now there is an issue with addCallback
        alarmDB = Room.databaseBuilder(c.getApplicationContext(), AlarmDatabase.class, "AlarmDB").addCallback(myCallBack).build();

        //ciDBi submit button
        mSubmitButton = v.findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //assigning values to the data
//                Boolean mDays = mDayCheckboxes.toString();
                String mTime = mAlarmField.getText().toString();
//                String mActId =

                Alarm a1 = new Alarm(true, mTime, ""); // !! missing fields

                addAlarmInBackground(a1);

                alarmDB.getAlarmDAO().addAlarm(a1);

                // the list of Alarms... may be moved elsewhere
                alarmList = alarmDB.getAlarmDAO().getAllAlarm();

                getAlarmListBackground();
//                StringBuilder sb = new StringBuilder();
//                for(Alarm p : alarmList){
//                    sb.append(p.getId()+" :"+p.getTime() + ": "+p.getDays()); //random info, incorrect
//                    sb.append("\n");
//                }
//                String finalData = sb.toString();
//                //Toast.makeText(AlarmFragment.this, ""+finalData , Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), finalData, Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }


    public void addAlarmInBackground(Alarm alarm){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                alarmDB.getAlarmDAO().addAlarm(alarm);

                // on finishing task
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Added to Database", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void getAlarmListBackground(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                alarmList = alarmDB.getAlarmDAO().getAllAlarm();

                // on finishing task
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder sb = new StringBuilder();
                        for(Alarm p : alarmList){
                            sb.append(p.getId()+" :"+p.getTime() + ": "+p.getDays()); //random info, incorrect
                            sb.append("\n");
                        }
                        String finalData = sb.toString();
                        Toast.makeText(getActivity(), finalData, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
