package com.example.zooalarm.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zooalarm.database.Alarm;
import com.example.zooalarm.database.AlarmLab;
import com.example.zooalarm.R;
import com.example.zooalarm.ui.activities.AlarmCreateActivity;
import com.example.zooalarm.ui.activities.MainActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AlarmListFragment extends Fragment implements View.OnClickListener{
    private RecyclerView mAlarmRecyclerView;
    private AlarmAdapter mAdapter;
    private Button mAlarmButton;
    private Button mBackButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        mAlarmRecyclerView = (RecyclerView) view
                .findViewById(R.id.alarm_recycler_view);
        mAlarmRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Initialize your buttons and set click listeners
        mAlarmButton = view.findViewById(R.id.addAlarmButton);
        mBackButton = view.findViewById(R.id.backButton);
        mAlarmButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        updateUI();
        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    private void updateUI() {
        AlarmLab alarmLab = AlarmLab.get(getActivity());
        List<Alarm> alarms = alarmLab.getAlarms();

        sortByTime(alarms);

        if (mAdapter == null) {
            mAdapter = new AlarmAdapter(alarms);
            mAlarmRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setAlarms(alarms);
            mAdapter.notifyDataSetChanged();
        }
    }

    public static void sortByTime(List<Alarm> alarms) {
        Collections.sort(alarms, new Comparator<Alarm>() {
            @Override
            public int compare(Alarm alarm1, Alarm alarm2) {
                String time1 = alarm1.getTime();
                String time2 = alarm2.getTime();
                String[] parts1 = time1.split("[:\\s]");
                String[] parts2 = time2.split("[:\\s]");

                // Convert the hour parts to integers
                int hour1 = Integer.parseInt(parts1[0]);
                int hour2 = Integer.parseInt(parts2[0]);

                // Convert minutes parts to integers
                int minute1 = Integer.parseInt(parts1[1]);
                int minute2 = Integer.parseInt(parts2[1]);

                // Convert AM/PM to integers (AM=0, PM=1)
                int amPm1 = parts1[2].equalsIgnoreCase("AM") ? 0 : 1;
                int amPm2 = parts2[2].equalsIgnoreCase("AM") ? 0 : 1;

                // Compare AM/PM first, then by hour, and finally by minute
                if (amPm1 != amPm2) {
                    return Integer.compare(amPm1, amPm2); // AM comes before PM
                } else if (hour1 != hour2) {
                    return Integer.compare(hour1, hour2); // Compare by hour
                } else {
                    return Integer.compare(minute1, minute2); // Compare by minute
                }
            }
        });

    }

    private class AlarmHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTimeTextView;
        private TextView mTitleTextView;

        private Alarm mAlarm;
        public void bind(Alarm alarm) {
            mAlarm = alarm;
            mTimeTextView.setText(mAlarm.getTime());
            mTitleTextView.setText(mAlarm.getTitle());
        }
        public AlarmHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_alarm, parent, false));
            itemView.setOnClickListener(this);
            mTimeTextView = (TextView) itemView.findViewById(R.id.alarm_time);
            mTitleTextView = (TextView) itemView.findViewById(R.id.alarm_title);
        }
        @Override
        public void onClick(View view) {
            Intent intent = AlarmCreateActivity.newIntent(getActivity(), mAlarm.getId());
            startActivity(intent);
        }
    }


    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.addAlarmButton){
            startActivity(new Intent(getActivity(), AlarmCreateActivity.class));
        }else if(viewId==R.id.backButton){
            startActivity(new Intent(getActivity(), MainActivity.class));

        }
    }


    private class AlarmAdapter extends RecyclerView.Adapter<AlarmHolder> {
        private List<Alarm> mAlarms;
        public AlarmAdapter(List<Alarm> alarms) {
            mAlarms = alarms;
        }
        @Override
        public AlarmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new AlarmHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(AlarmHolder holder, int position) {
            Alarm alarm = mAlarms.get(position);
            holder.bind(alarm);
        }
        @Override
        public int getItemCount() {
            return mAlarms.size();
        }
        public void setAlarms(List<Alarm> alarms) {
            mAlarms = alarms;
        }
    }
}
