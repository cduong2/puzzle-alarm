package com.example.zooalarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlarmListFragment extends Fragment {
    private RecyclerView mAlarmRecyclerView;
    private AlarmAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        mAlarmRecyclerView = (RecyclerView) view
                .findViewById(R.id.alarm_recycler_view);
        mAlarmRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;

    }
    private void updateUI() {
        AlarmLab alarmLab = AlarmLab.get(getActivity());
        List<Alarm> crimes = alarmLab.getAlarms();
        mAdapter = new AlarmAdapter(crimes);
        mAlarmRecyclerView.setAdapter(mAdapter);
    }
    private class AlarmHolder extends RecyclerView.ViewHolder {
        public AlarmHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_alarm, parent, false));
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
        }
        @Override
        public int getItemCount() {
            return mAlarms.size();
        }
    }
}
