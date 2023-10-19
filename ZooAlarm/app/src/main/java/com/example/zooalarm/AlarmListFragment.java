package com.example.zooalarm;

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
    private void updateUI() {
        AlarmLab alarmLab = AlarmLab.get(getActivity());
        List<Alarm> alarms = alarmLab.getAlarms();
        mAdapter = new AlarmAdapter(alarms);
        mAlarmRecyclerView.setAdapter(mAdapter);
    }
    private class AlarmHolder extends RecyclerView.ViewHolder {
        private TextView mTimeTextView;
        private TextView mActivityTextView;
        private Alarm mAlarm;
        public void bind(Alarm alarm) {
            mAlarm = alarm;
            mTimeTextView.setText(mAlarm.getTime());
            mActivityTextView.setText(mAlarm.getActivity());
        }
        public AlarmHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_alarm, parent, false));
            mTimeTextView = (TextView) itemView.findViewById(R.id.alarm_title);
            mActivityTextView = (TextView) itemView.findViewById(R.id.alarm_activity);
        } }


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
    }
}
